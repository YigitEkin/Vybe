package com.vybe.backend.model.entity;

import com.sun.istack.NotNull;
import com.vybe.backend.repository.SongNodeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

/**
 * Playlist class that will govern the restrictions and the next song playing
 * @author Oğuz Ata Çal
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    /**
     * unique id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true)
    private String defaultPlaylistId;

    @Column(unique = true)
    private String requestPlaylistId;

    @OneToOne( mappedBy = "playlist")
    private Venue venue;

    private Integer defaultPlaylistIndex;

    /**
     * Priority queue of requested songs, based on weight of song
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "playlistId")
    private Set<SongNode> requestedSongs;

    /**
     * Default song queue playlist to play when there are no requested songs
     */
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "song_playlist",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    private Set<Song> defaultPlaylist;

    /**
     * List of genres that the song requests are permitted in
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "playlist_permitted_genres", joinColumns = @JoinColumn(name = "playlist_id"))
    @Column(name = "permitted_genre")
    private Set<String> permittedGenres;

    /**
     * List of genres that the song requests are not permitted in
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "playlist_banned_genres", joinColumns = @JoinColumn(name = "playlist_id"))
    @Column(name = "banned_genre")
    private Set<String> bannedGenres;

    /**
     * Reference to the currently playing song
     */
    @Transient
    private Song currentlyPlayingSong;

    /**
     * Current mode of the playlist, to see if its playing default or requested songs
     */
    private String currentMode;

   
    /**
     * Adds a song to the default playlist
     * @param song the song that will be added to the default playlist
     * @return TRUE if the song was added successfully, FALSE otherwise 
     */
    public Boolean addSong(Song song) {
        defaultPlaylist.add(song);
        return true;
    }

    /**
     * Adds a song request to the song requests queue
     * @param songNode the song request that will be added to the requests queue
     * @return TRUE if the song request was added successfully, FALSE otherwise 
     */
    // TODO: change to song request
    public Boolean addSongRequest(SongNode songNode) {
        requestedSongs.add(songNode);
        return true;
    }

    /**
     * Removes the next song from the appropriate queue and returns it
     * @return next song
     */
    public Song playNextSong(SongNodeRepository songNodeRepository) {
        if(requestedSongs.size() > 0) {
            currentMode = "request";
            // find the max song from the requested songs using the compareTo method
            SongNode maxWeightSong = requestedSongs.stream().max(SongNode::compareTo).get();
            requestedSongs.remove(maxWeightSong);
            currentlyPlayingSong = maxWeightSong.getSong();
            return maxWeightSong.getSong();
        } else {
            currentMode = "default";
            currentlyPlayingSong = defaultPlaylist.stream().toList().get(defaultPlaylistIndex);
            defaultPlaylistIndex = (defaultPlaylistIndex + 1) % defaultPlaylist.size();
            return currentlyPlayingSong;
        }
    }

    public List<Song> getCurrentQueue(int numSongs){
        List<Song> nextSongs = new ArrayList<>();
        List<SongNode> sortedRequestedSongs = new ArrayList<>(requestedSongs);
        sortedRequestedSongs.sort(Collections.reverseOrder());
        int requestIndex = 0;
        int defaultIndex = defaultPlaylistIndex;

        for (int i = 0; i < numSongs; i++){
            if(requestIndex < requestedSongs.size()){
                SongNode maxWeightSong = sortedRequestedSongs.get(requestIndex);
                nextSongs.add(maxWeightSong.getSong());
                requestIndex++;
            } else {
                nextSongs.add(defaultPlaylist.stream().toList().get(defaultIndex));
                defaultIndex = (defaultIndex + 1) % defaultPlaylist.size();
            }
        }
        return nextSongs;
    }
}

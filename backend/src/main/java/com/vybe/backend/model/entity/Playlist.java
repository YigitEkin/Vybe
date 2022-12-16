package com.vybe.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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


    /**
     * Priority queue of requested songs, based on weight of song
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id")
    // TODO: supposed to be priority queue
    private List<SongNode> requestedSongs;

    /**
     * Default song queue playlist to play when there are no requested songs
     */
    @ManyToMany
    @JoinTable(
            name = "song_playlist",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    // TODO: supposed to be queue
    private List<Song> defaultPlaylist;

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
    @Transient
    private String currentMode;

   
    /**
     * Adds a song to the default playlist
     * @param song the song that will be added to the default playlist
     * @return TRUE if the song was added successfully, FALSE otherwise 
     */
    public Boolean addSong(Song song) {
        return null;
    }

    /**
     * Adds a song request to the song requests queue
     * @param songRequest the song request that will be added to the requests queue
     * @return TRUE if the song request was added successfully, FALSE otherwise 
     */
    public Boolean addSongRequest(SongRequest songRequest) {
        return null;
    }

    /**
     * Removes the next song from the appropriate queue and returns it
     * @return next song
     */
    public Song playNextSong() {
        return null;
    }
}

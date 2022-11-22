package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Playlist class that will govern the restrictions and the next song playing
 * @author Oğuz Ata Çal
 */
@Data
@Entity
public class Playlist {
    /**
     * unique id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


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
    @ElementCollection
    @CollectionTable(name = "playlist_permitted_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "permitted_genres")
    private List<String> permittedGenres;

    /**
     * List of genres that the song requests are not permitted in
     */
    @ElementCollection
    @CollectionTable(name = "playlist_banned_genres", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "banned_genres")
    private List<String> bannedGenres;

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
     * Adds a permitted genre to the permitted genres list
     * @param genre the genre that will be added to the list
     * @return TRUE if the genre was added successfully, FALSE otherwise 
     */
    public Boolean addPermittedGenre(String genre) {
        return null;
    }

    /**
     * Removes a permitted genre from the permitted genres list
     * @param genre the genre that will be removed from the list
     * @return TRUE if the genre was removed successfully, FALSE otherwise 
     */
    public Boolean removePermittedGenre(String genre) {
        return null;
    }

    /**
     * Adds a banned genre to the permitted banned list
     * @param genre the genre that will be added to the list
     * @return TRUE if the genre was added successfully, FALSE otherwise
     */
    public Boolean addBannedGenre(String genre) {
        return null;
    }

    /**
     * Removes a banned genre from the banned genres list
     * @param genre the genre that will be removed from the list
     * @return TRUE if the genre was removed successfully, FALSE otherwise
     */
    public Boolean removeBannedGenre(String genre) {
        return null;
    }

    /**
     * Adds a song to the default playlist
     * @param Song the song that will be added to the default playlist
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

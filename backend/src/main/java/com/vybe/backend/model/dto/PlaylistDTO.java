package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.SongNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistDTO {
    private Integer id;
    // TODO: supposed to be priority queue
    private Set<SongNode> requestedSongs;
    private Integer venueId;
    // TODO: supposed to be queue
    private Set<Song> defaultPlaylist;
    private Set<String> permittedGenres;
    private Set<String> bannedGenres;
    private Song currentlyPlayingSong;
    private String currentMode;
    private String defaultPlaylistId;
    private String requestPlaylistId;

    public PlaylistDTO(Playlist playlist) {
        this.id = playlist.getId();
        this.venueId = playlist.getVenue().getId();
        this.requestedSongs = playlist.getRequestedSongs();
        this.defaultPlaylist = playlist.getDefaultPlaylist();
        this.permittedGenres = playlist.getPermittedGenres();
        this.bannedGenres = playlist.getBannedGenres();
        this.currentlyPlayingSong = playlist.getCurrentlyPlayingSong();
        this.currentMode = playlist.getCurrentMode();
        this.defaultPlaylistId = playlist.getDefaultPlaylistId();
        this.requestPlaylistId = playlist.getRequestPlaylistId();
    }


}

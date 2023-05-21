package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistCreationDTO {
    private Integer venueId;
    private String defaultPlaylistId;
    private String requestPlaylistId;


    public Playlist toPlaylist() {
        return new Playlist(0, defaultPlaylistId, requestPlaylistId, null,0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), null, "default");
    }
}

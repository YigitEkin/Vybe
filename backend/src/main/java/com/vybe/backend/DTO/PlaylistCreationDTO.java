package com.vybe.backend.DTO;

import com.vybe.backend.model.entity.Playlist;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistCreationDTO {
    private Integer venueId;


    public Playlist toPlaylist() {
        return new Playlist(0, null, null, new ArrayList<Song>(), new HashSet<>(), new HashSet<>(), null, null);
    }
}

package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongDTO {
    private String id;
    private String name;
    private String artist;
    // TODO: Decide on Object type
    private String albumArt;
    private String link;
    private String soundtrackYBId;

    public SongDTO(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.artist = song.getArtist();
        this.albumArt = song.getAlbumArt();
        this.link = song.getLink();
    }

    public Song toSong() {
        return new Song(id, name, artist, albumArt, link);
    }
}

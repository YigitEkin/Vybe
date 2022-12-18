package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Song;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SongDTO {
    private Integer id;
    private String name;
    private String artist;
    // TODO: Decide on Object type
    private String albumArt;
    private String link;

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

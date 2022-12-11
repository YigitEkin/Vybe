package com.vybe.backend.DTO;


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

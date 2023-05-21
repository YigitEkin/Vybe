package com.vybe.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vybe.backend.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(value = { "playlist", "analytics" })
public class VenueDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private Analytics analytics;
    private Set<Image> photos;
    private Playlist playlist;
    private String qrCode;
    private String soundzoneId;
    private String token;
    private SongDTO currentSong;
    @Nullable
    private Double rating;

    public VenueDTO(Venue venue) {
        if (venue != null) {
            this.id = venue.getId();
            this.name = venue.getName();
            this.description = venue.getDescription();
            this.location = venue.getLocation();
            this.analytics = venue.getAnalytics();
            this.photos = venue.getPhotos();
            this.playlist = venue.getPlaylist();
            this.qrCode = venue.getQrCode();
            this.soundzoneId = venue.getSoundzoneId();
            this.token = venue.getToken();
            this.currentSong = null;
            if (venue.getCurrentSong() != null)
                this.currentSong = new SongDTO(venue.getCurrentSong());
            this.rating = venue.getRating();
        }
    }

    public Venue toVenue() {
      return new Venue(id, name, description, location, analytics, photos, playlist, null, null, null, qrCode, token, soundzoneId, null, rating);
    }
}

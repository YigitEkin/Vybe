package com.vybe.backend.DTO;

import com.vybe.backend.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VenueDTO {
    private int id;
    private String name;
    private String description;
    private String location;
    private Analytics analytics;
    private List<Object> photos;
    private Playlist playlist;
    private List<Rating> ratings;
    private List<Comment> comments;
    private String qrCode;
    private String soundzoneId;

    public VenueDTO(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.description = venue.getDescription();
        this.location = venue.getLocation();
        this.analytics = venue.getAnalytics();
        this.photos = venue.getPhotos();
        this.playlist = venue.getPlaylist();
        this.ratings = venue.getRatings();
        this.comments = venue.getComments();
        this.qrCode = venue.getQrCode();
        this.soundzoneId = venue.getSoundzoneId();
    }

    public Venue toVenue() {
        return new Venue(id, name, description, location, analytics, photos, playlist, null, ratings, comments, qrCode, soundzoneId);
    }
}

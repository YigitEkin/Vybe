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
    private List<Playlist> playlist;
    private Playlist currentPlaylist;
    private List<Rating> ratings;
    private List<Comment> comments;
    private String qrCode;

    public VenueDTO(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.description = venue.getDescription();
        this.location = venue.getLocation();
        this.analytics = venue.getAnalytics();
        this.photos = venue.getPhotos();
        this.playlist = venue.getPlaylist();
        this.currentPlaylist = venue.getCurrentPlaylist();
        this.ratings = venue.getRatings();
        this.comments = venue.getComments();
        this.qrCode = venue.getQrCode();
    }

    public Venue toVenue() {
        return new Venue(id, name, description, location, analytics, photos, playlist, currentPlaylist, null, ratings, comments, qrCode);
    }
}

package com.vybe.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vybe.backend.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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
    private List<Object> photos;
    private Playlist playlist;
    private Set<Rating> ratings;
    private Set<CommentDTO> comments;
    private String qrCode;
    private String soundzoneId;
    private String token;

    public VenueDTO(Venue venue) {
        if (venue != null) {
            this.id = venue.getId();
            this.name = venue.getName();
            this.description = venue.getDescription();
            this.location = venue.getLocation();
            this.analytics = venue.getAnalytics();
            this.photos = venue.getPhotos();
            this.playlist = venue.getPlaylist();
            this.ratings = venue.getRatings();
            this.comments = CommentDTO.toCommentDTOSet(venue.getComments());
            this.qrCode = venue.getQrCode();
            this.soundzoneId = venue.getSoundzoneId();
            this.token = venue.getToken();
        }
    }

    public Venue toVenue() {
        return new Venue(id, name, description, location, analytics, photos, playlist, null, ratings, null, qrCode, token, soundzoneId);
    }
}

package com.vybe.backend.model.entity;

import java.util.List;

public class Venue {
    private String name;
    private String description;
    private String location;
    private Analytics analytics;
    private List<Object> photos;
    private List<Playlist> playlist;
    private Playlist currentPlaylist;
    private List<Customer> checkedInCustomers;
    private List<Rating> ratings;
    private List<Comment> comments;
    private String qrCode;

    public Boolean increasePoints(Integer points) {
        return null;
    }

    public Boolean acquireBadge(String badgeName) {
        return null;
    }

    public Boolean createPlaylist() {
        return null;
    }

    public Boolean migratePlaylistFromSpotify(String url) {
        return null;
    }
}

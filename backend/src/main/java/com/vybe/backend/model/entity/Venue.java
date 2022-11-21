package com.vybe.backend.model.entity;

import java.util.List;

/**
 * Venue class, representation of physical venues in the database
 * @author Harun Can Surav
 */
public class Venue {
    /**
     * Venue id that will be used for object identification
     */
    private Integer id;
    /**
     * Name of the venue that will be shown in map and used in search
     */
    private String name;
    /**
     * Venue description that will be shown in the venue page
     */
    private String description;
    /**
     * Venue location that will be used in map
     */
    private String location;
    /**
     * Analytics class that will store badges, points and other analytics data
     */
    private Analytics analytics;
    /**
     * Photos of the venue
     */
    //TODO: Decide on Object type
    private List<Object> photos;
    /**
     * List of playlists defined for the venue
     */
    private List<Playlist> playlist;
    /**
     * Active playlist
     */
    private Playlist currentPlaylist;
    /**
     * List of users that are currently checked in
     */
    private List<Customer> checkedInCustomers;
    /**
     * List of ratings left to the venue
     */
    private List<Rating> ratings;
    /**
     * List of comments left to the venue
     */
    private List<Comment> comments;
    /**
     * QR code of the venue
     */
    private String qrCode;

    /**
     * Called when coins are spent on the venue
     * @param points the amount of points to be added to the venue
     * @return TRUE if the points were added successfully, FALSE otherwise
     */
    public Boolean increasePoints(Integer points) {
        return null;
    }

    /**
     * Called when a venue is eligible for a badge
     * @param badgeName is the name of the badge that will be added to the venue
     * @return TRUE if the badge was added successfully, FALSE otherwise
     */
    public Boolean acquireBadge(String badgeName) {
        return null;
    }

    /**
     * Called when creating an empty playlist for the venue
     * @return TRUE if the customer was added successfully, FALSE otherwise
     */
    public Boolean createPlaylist() {
        return null;
    }

    /**
     * Called when a venue admin wants to migrate their playlist to the venue
     * @param url is the url of the playlist on Spotify
     * @return TRUE if the playlist was migrated successfully, FALSE otherwise
     */
    public Boolean migratePlaylistFromSpotify(String url) {
        return null;
    }
}

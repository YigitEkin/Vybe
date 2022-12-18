package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Venue class, representation of physical venues in the database
 * @author Harun Can Surav
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Venue {
    /**
     * Venue id that will be used for object identification
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne
    @JoinColumn(name = "analytics_id", referencedColumnName = "id")
    private Analytics analytics;

    /**
     * Photos of the venue
     */
    //TODO: Decide on Object type
    @Transient
    private List<Object> photos;

    /**
     * List of playlists defined for the venue
     */
    @OneToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Playlist playlist;


    /**
     * List of users that are currently checked in
     */
    @Transient
    private List<Customer> checkedInCustomers;

    /**
     * List of ratings left to the venue
     */
    @OneToMany(
            mappedBy = "venue",
            cascade = CascadeType.ALL)
    private List<Rating> ratings;

    /**
     * List of comments left to the venue
     */
    @OneToMany(
            mappedBy = "venue",
            cascade = CascadeType.ALL
    )
    private List<Comment> comments;

    /**
     * QR code of the venue
     */
    private String qrCode;

    private String token;

    private String soundzoneId;

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
     * Called when a venue admin wants to migrate their playlist to the venue
     * @param url is the url of the playlist on Spotify
     * @return TRUE if the playlist was migrated successfully, FALSE otherwise
     */
    public Boolean migratePlaylistFromSpotify(String url) {
        return null;
    }
}

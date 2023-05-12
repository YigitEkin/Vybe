package com.vybe.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

/**
 * Venue class, representation of physical venues in the database
 * @author Harun Can Surav
 */
@Getter
@Setter
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

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Image> photos;

    /**
     * List of playlists defined for the venue
     */
    @OneToOne(fetch = FetchType.EAGER,
              cascade = CascadeType.ALL
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Playlist playlist;


    /**
     * List of users that are currently checked in
     */
    @OneToMany(
            mappedBy = "checkedInVenue",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Customer> checkedInCustomers;

    /**
     * List of ratings left to the venue
     */
    @OneToMany(
            mappedBy = "venue",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Rating> ratings;

    /**
     * List of comments left to the venue
     */
    @OneToMany(
            mappedBy = "venue",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Comment> comments;

    /**
     * QR code of the venue
     */
    private String qrCode;

    private String token;

    private String soundzoneId;

    @ManyToOne
    private Song currentSong;

    private Double rating;

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

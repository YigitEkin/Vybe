package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Customer class that is the representation of each customer
 * @author Oğuz Ata Çal
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    public Customer(String username, String password, String phoneNumber, Object profilePicture, Venue currentVenue, Stack<SongRequest> requests, List<Customer> friendships,
                    String dateOfBirth, String dateOfCreation) {
        super(username, password, phoneNumber, profilePicture, currentVenue, requests);
        this.friends = friendships;
        this.dateOfBirth = dateOfBirth;
        this.dateOfCreation = dateOfCreation;

    }


    /**
     * date of birth of the customer
     */
    private String dateOfBirth;

    /**
     * Analytics object that holds statistics of the customer
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "analytics_id")
    private Analytics analytics;

    /**
     * Wallet object that holds financial information of the customer
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    /**
     * list that holds the friends of the holder customer
     */
    @OneToMany( mappedBy = "username")
    private List<Customer> friends;

    // TODO: map streaks to database
    /**
     * hashmap to holds the streak information of the user to venues
     * (key, value) pair is (venue.id, streak_count)
     */
    @Transient
    private HashMap<Integer, Integer> streaks;

    /**
     * date of creation of the customer account
     */
    private String dateOfCreation;

    /**
     * List of comments of customer
     */
    @OneToMany(
            mappedBy = "commentedBy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Comment> comments;

    /**
     * Checking the customer in to a venue (entering a venue)
     * @param venueId id of the venue that the customer is being checked in to
     * @return TRUE if the checkin was successful, FALSE otherwise
     */
    public Boolean checkIn(Integer venueId) {
        return null;
    }

    /**
     * Checking the customer out from his current venue (exiting a venue)
     * @return TRUE if the checkout was successful, FALSE otherwise
     */
    public Boolean checkOut() {
        return null;
    }

    /**
     * checking the location of the customer and seeing if he is in the venue he is checked into
     * @return TRUE if the customers location is in the bounds of his current venue, FALSE otherwise
     */
    public Boolean checkLocation() {
        return null;
    }

    /**
     * Adds the specified badge to this customer
     * @param badgeName is the name of the badge that will be added to the customer
     * @return TRUE if the badge was added successfully, FALSE otherwise
     */
    public Boolean acquireBadge(String badgeName) {
        return null;
    }

    /**
     * Increases the streak of this customer with venue with venueId by 1
     * @param venueId id of the venue that the streak will be increased with
     * @return streak of the customer for that venue after the increase
     */
    public Integer increaseStreak(Integer venueId) {
        return null;
    }

    /**
     * Resets the streak of this customer with venue with venueId to 0
     * @param venueId id of the venue that the streak will be reset
     * @return streak of the customer for that venue after the reset
     */
    public Integer resetStreak(Integer venueId) {
        return null;
    }
    
    /**
     * Increases the points of the customer by a variable amount
     * @param points amount that the points will be increased
     * @return amount of points of the customer after the increase
     */
    public Integer increasePoints(Integer points) {
        return null;
    }
}

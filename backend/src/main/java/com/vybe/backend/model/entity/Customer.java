package com.vybe.backend.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Customer class that is the representation of each customer
 * @author Oğuz Ata Çal
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    public Customer(String username, String name, String surname, String password, String phoneNumber, Object profilePicture, Venue currentVenue, Stack<SongRequest> requests, List<Customer> friendships,
                    String dateOfBirth, String dateOfCreation) {
        super(username, name, surname, password, phoneNumber, profilePicture, currentVenue, requests);
        this.wallet = new Wallet(0.0,0.0);
        this.friends = friendships;
        this.dateOfBirth = dateOfBirth;
        this.dateOfCreation = dateOfCreation;
        this.streaks =  new ArrayList<>();
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
    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Friendship> sentFriendRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE)
    private List<Friendship> receivedFriendRequests;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "friendship",
            joinColumns = @JoinColumn(name = "sender_customer_username"),
            inverseJoinColumns = @JoinColumn(name = "receiver_customer_username")
    )
    private List<Customer> friends;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Streak> streaks;

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

    @ManyToOne
    private Venue checkedInVenue;

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
     * Increases the points of the customer by a variable amount
     * @param points amount that the points will be increased
     * @return amount of points of the customer after the increase
     */
    public Integer increasePoints(Integer points) {
        return null;
    }
}

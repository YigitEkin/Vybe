package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Stack;

/**
 * VenueAdmin class, representation of venue admins in the database
 * @author Harun Can Surav
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueAdmin extends User {

    //TODO: one to many or one to one, decide if we want to have multiple admins for a venue
    //@OneToOne
    //private Venue venue;

    private Boolean enabled;

    public VenueAdmin(String username, String password, String phoneNumber, Object profilePicture, Venue currentVenue, Stack<SongRequest> requests, Boolean enabled) {
        super(username, password, phoneNumber, profilePicture, currentVenue, requests);
        this.enabled = enabled;
    }
}

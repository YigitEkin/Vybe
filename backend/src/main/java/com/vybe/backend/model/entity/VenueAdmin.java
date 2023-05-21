package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import java.util.Stack;

/**
 * VenueAdmin class, representation of venue admins in the database
 * @author Harun Can Surav
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenueAdmin extends User {

    @OneToOne
    private Venue venue;

    private Boolean enabled;

    public VenueAdmin(String username, String name, String surname, String password, String phoneNumber, Image profilePicture, Venue currentVenue, Stack<SongRequest> requests, Boolean enabled, Venue venue) {
        super(username, name, surname, password, phoneNumber, profilePicture, currentVenue, requests);
        this.enabled = enabled;
        this.venue = venue;
    }

}

package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.Entity;
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

    //TODO: one to many or one to one, decide if we want to have multiple admins for a venue
    //@OneToOne
    //private Venue venue;

    private Boolean enabled;

    public VenueAdmin(String username, String name, String surname, String password, String phoneNumber, Image profilePicture, Venue currentVenue, Stack<SongRequest> requests, Boolean enabled) {
        super(username, name, surname, password, phoneNumber, profilePicture, currentVenue, requests);
        this.enabled = enabled;
    }

}

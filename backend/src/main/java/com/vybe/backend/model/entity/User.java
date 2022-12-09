package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Stack;

/**
 * User class that will be inherited by Customer and VenueAdmin classes
 * @author Harun Can Surav
 */
@Data
@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * The users username, unique
     */
    @Id
    private String username;
    /**
     * The users password
     */
    private String password;
    /**
     * The users email
     */
    private String email;
    /**
     * The users phone number
     */
    private String phoneNumber;
    /**
     * The users profile picture
     */
    //TODO: Decide on Object type
    @Transient
    private Object profilePicture;
    /**
     * The venue user is currently checked in
     */
    @Transient
    private Venue currentVenue;
    /**
     * The song requests issued by the user
     */
    // should it be transient?
    @Transient
    private Stack<SongRequest> requests;
}

package com.vybe.backend.model.entity;

import java.util.Stack;

/**
 * User class that will be inherited by Customer and VenueAdmin classes
 * @author Harun Can Surav
 */
public class User {
    /**
     * The users username, unique
     */
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
    private Object profilePicture;
    /**
     * The venue user is currently checked in
     */
    private Venue currentVenue;
    /**
     * The song requests issued by the user
     */
    private Stack<SongRequest> requests;
}

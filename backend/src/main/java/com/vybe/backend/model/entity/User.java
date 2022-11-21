package com.vybe.backend.model.entity;

import java.util.Stack;

public class User {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Object profilePicture;
    private Venue currentVenue;
    private Stack<SongRequest> requests;
}

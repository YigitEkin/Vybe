package com.vybe.backend.model.entity;

import java.util.HashMap;
import java.util.List;

public class Customer extends User {
    private String dateOfBirth;
    private Analytics analytics;
    private Wallet wallet;
    private List<Customer> friends;
    private HashMap<Integer, Integer> streaks;
    private String dateOfCreation;

    public Boolean checkIn(Integer venueId) {
        return null;
    }

    public Boolean checkOut() {
        return null;
    }

    public Boolean checkLocation() {
        return null;
    }

    public Boolean acquireBadge(String badgeName) {
        return null;
    }

    public Integer increaseStreak(Integer venueId) {
        return null;
    }

    public Integer increasePoints(Integer points) {
        return null;
    }
}

package com.vybe.backend.model.entity;

public class Comment {

    private Integer id;
    private String text;
    private String date;
    private Customer commentedBy;
    private Venue venue;
}

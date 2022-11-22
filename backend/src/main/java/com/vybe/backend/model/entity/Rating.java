package com.vybe.backend.model.entity;

/**
 * Rating class that holds the information of the rating that a customer gave to a venue
 * @author Oğuz Ata Çal
 */
public class Rating {

    /**
     * Unique id of the rating
     */
    private Integer id;

    /**
     * The actual rating that the user gave
     */
    private Double rating;

    /**
     * The user that the rating is given by
     */
    private User ratedBy;

    /**
     * The venue that the rating was given on
     */
    private Venue venue;

}

package com.vybe.backend.model.entity;

/**
 * Comment class that holds the information of a comment on a venue by a customer
 * @author Oğuz Ata Çal
 */
public class Comment {
    /**
     * Unique id of the comment
     */
    private Integer id;

    /**
     * Text content of the comment
     */
    private String text;

    /**
     * Date that the comment was written
     */
    private String date;

    /**
     * The customer that the comment belongs to
     */
    private Customer commentedBy;

    /**
     * The venue that was commented on
     */
    private Venue venue;
}

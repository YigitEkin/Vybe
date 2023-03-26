package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Comment class that holds the information of a comment on a venue by a customer
 * @author Oğuz Ata Çal
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    /**
     * Unique id of the comment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_username")
    private Customer commentedBy;

    /**
     * The venue that was commented on
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;
}

package com.vybe.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * The customer that the comment belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer commentedBy;

    /**
     * The venue that was commented on
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;
}

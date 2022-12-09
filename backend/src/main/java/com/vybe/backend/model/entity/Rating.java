package com.vybe.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Rating class that holds the information of the rating that a customer gave to a venue
 * @author Oğuz Ata Çal
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    /**
     * Unique id of the rating
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The actual rating that the user gave
     */
    private Double rating;

    /**
     * The customer that the rating is given by
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_username", referencedColumnName = "username")
    private Customer ratedBy;

    /**
     * The venue that the rating was given on
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

}

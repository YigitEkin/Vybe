package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Customer class that is the representation of each customer
 * @author Oğuz Ata Çal
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sender_customer_username", "receiver_customer_username"}))
public class Friendship {
    /*
     * unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * sender is the sender of the friend request
     */
    @ManyToOne
    @JoinColumn(name = "sender_customer_username")
    private Customer sender;

    /*
     * receiver is the receiver of the friend request
     */
    @ManyToOne
    @JoinColumn(name = "receiver_customer_username")
    private Customer receiver;

    /*
     * true if user1 has accepted user2's friend request
     * false if user1 has not accepted user2's friend request
     */
    private boolean accepted;
}
package com.vybe.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Customer class that is the representation of each customer
 * @author Oğuz Ata Çal
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "sender_customer_username", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer sender;

    /*
     * receiver is the receiver of the friend request
     */
    @ManyToOne
    @JoinColumn(name = "receiver_customer_username", referencedColumnName = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer receiver;

    /*
     * true if user1 has accepted user2's friend request
     * false if user1 has not accepted user2's friend request
     */
    private boolean accepted;
}
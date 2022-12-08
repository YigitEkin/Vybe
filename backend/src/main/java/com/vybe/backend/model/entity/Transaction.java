package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Transaction class that will be used as for representing the user transactions
 * @author Harun Can Surav
 */
@Entity
@Data
public class Transaction {
    /**
     * Unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The transaction value in USD
     */
    private Double paidAmount;
    /**
     * The transaction type (cash or ad)
     */
    private String transactionType;
    /**
     * Coins purchased for paidAmount
     */
    private Double receivedCoins;
    /**
     * The transaction date
     */
    private String date;
}

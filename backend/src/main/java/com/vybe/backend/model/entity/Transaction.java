package com.vybe.backend.model.entity;

import com.vybe.backend.model.enums.TransactionTypes;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Transaction class that will be used as for representing the user transactions
 * @author Harun Can Surav
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private TransactionTypes transactionType;
    /**
     * Coins purchased for paidAmount
     */
    private Double receivedCoins;
    /**
     * The transaction date
     */
    private String transactionDate;
}

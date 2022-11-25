package com.vybe.backend.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Wallet class, representation of customer wallets in the database
 * @author Harun Can Surav
 */
@Entity
@Data
public class Wallet {
    /**
     * unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * Balance left in the wallet in coins
     */
    private Double balance;

    /**
     * Amount of money spent by the customer
     */
    private Double totalSpent;

    /**
     * List of transactions made by the customer
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private List<Transaction> transactions;

    /**
     * Called when a transaction is made
     * @param transaction instance of the transaction
     * @return TRUE if transaction is successful, FALSE otherwise
     */
    public Boolean executeTransaction(Transaction transaction) {
        return null;
    }
}

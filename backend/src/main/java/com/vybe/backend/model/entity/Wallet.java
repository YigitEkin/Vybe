package com.vybe.backend.model.entity;

import java.util.List;

/**
 * Wallet class, representation of customer wallets in the database
 * @author Harun Can Surav
 */
public class Wallet {
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

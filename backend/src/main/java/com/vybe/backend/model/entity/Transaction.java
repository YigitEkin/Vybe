package com.vybe.backend.model.entity;

/**
 * Transaction class that will be used as for representing the user transactions
 * @author Harun Can Surav
 */
public class Transaction {
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
    private Double recievedCoins;
    /**
     * The transaction date
     */
    private String date;
}

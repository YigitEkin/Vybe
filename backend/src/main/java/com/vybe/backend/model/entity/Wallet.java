package com.vybe.backend.model.entity;

import com.vybe.backend.model.enums.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Wallet class, representation of customer wallets in the database
 * @author Harun Can Surav
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_id")
    private Set<Transaction> transactions;

    /**
     * Called when a transaction is made
     * @param transaction instance of the transaction
     * @return TRUE if transaction is successful, FALSE otherwise
     */
    public void executeTransaction(Transaction transaction) {
        if(transaction.getTransactionType().equals(TransactionTypes.ADVERTISEMENT))
            transaction.setPaidAmount(0.0);
        transactions.add(transaction);
        balance += transaction.getReceivedCoins();
        if(transaction.getTransactionType().equals(TransactionTypes.CARD))
            totalSpent += transaction.getPaidAmount();
    }

    public Wallet(Double balance, Double totalSpent) {
        this.balance = balance;
        this.totalSpent = totalSpent;
        this.transactions = new HashSet<>();
    }
}

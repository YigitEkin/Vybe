package com.vybe.backend.model.entity;

import java.util.List;

public class Wallet {
    private Double balance;
    private Double totalSpent;
    private List<Transaction> transactions;

    public Boolean executeTransaction(Transaction transaction) {
        return null;
    }
}

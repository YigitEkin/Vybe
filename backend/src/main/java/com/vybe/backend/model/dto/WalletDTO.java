package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Transaction;
import com.vybe.backend.model.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WalletDTO {
    private Integer id;
    private Double balance;
    private Double totalSpent;
    private List<Transaction> transactions;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
        this.totalSpent = wallet.getTotalSpent();
        this.transactions = wallet.getTransactions();
    }
}

package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Transaction;
import com.vybe.backend.model.enums.TransactionTypes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Integer id;
    private Double paidAmount;
    private TransactionTypes transactionType;
    private Double receivedCoins;
    private String transactionDate;

    public Transaction toTransaction() {
        Transaction t = new Transaction();
        t.setId(id);
        t.setTransactionDate(transactionDate);
        t.setPaidAmount(paidAmount);
        t.setReceivedCoins(receivedCoins);
        t.setTransactionType(transactionType);
        return t;
    }

    public TransactionDTO(Transaction t) {
        this.setTransactionType(t.getTransactionType());
        this.setId(t.getId());
        this.setPaidAmount(t.getPaidAmount());
        this.setReceivedCoins(t.getReceivedCoins());
        this.setTransactionDate(t.getTransactionDate());
    }
}

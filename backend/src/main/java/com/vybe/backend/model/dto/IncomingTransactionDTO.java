package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Transaction;
import com.vybe.backend.model.enums.TransactionTypes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomingTransactionDTO {

    private TransactionTypes transactionType;

    private Double receivedCoins;

    private String date;

    private String name;

    private String surname;

    private String cardNumber;

    private String expirationMonth;

    private String expirationYear;

    private String cvc;

    public Transaction toTransaction() {
        Transaction t = new Transaction();
        t.setTransactionDate(date);
        t.setPaidAmount(receivedCoins * 0.05);
        t.setReceivedCoins(receivedCoins);
        t.setTransactionType(transactionType);
        return t;
    }
}

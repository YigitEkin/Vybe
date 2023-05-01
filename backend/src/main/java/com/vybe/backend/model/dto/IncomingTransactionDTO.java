package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.Transaction;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomingTransactionDTO {

    private Double paidAmount;

    private String transactionType;

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
        t.setPaidAmount(paidAmount);
        t.setReceivedCoins(receivedCoins);
        t.setTransactionType(transactionType);
        return t;
    }
}

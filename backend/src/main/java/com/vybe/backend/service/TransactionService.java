package com.vybe.backend.service;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.TransactionNotFoundException;
import com.vybe.backend.exception.TransactionNotValidatedException;
import com.vybe.backend.exception.WalletNotFoundException;
import com.vybe.backend.model.dto.IncomingTransactionDTO;
import com.vybe.backend.model.dto.TransactionDTO;
import com.vybe.backend.model.dto.WalletDTO;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Transaction;
import com.vybe.backend.model.entity.Wallet;
import com.vybe.backend.model.enums.TransactionTypes;
import com.vybe.backend.repository.TransactionRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.WalletRepository;
import com.vybe.backend.util.IyzicoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Resource
    WalletRepository walletRepository;
    @Resource
    TransactionRepository transactionRepository;
    @Resource
    IyzicoUtil iyzicoUtil;
    @Resource
    UserRepository userRepository;

    @Autowired
    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public WalletDTO executeNewTransaction(IncomingTransactionDTO incomingTransaction, String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username " + username + " is not found.");
        }

        Customer c = (Customer) userRepository.findByUsername(username).get();

        Wallet wallet = c.getWallet();

        if(incomingTransaction.getTransactionType().equals(TransactionTypes.CARD)) {
            if(incomingTransaction.getCardNumber() == null || incomingTransaction.getCardNumber().length() != 16) {
                throw new TransactionNotValidatedException("Card number is not valid");
            }

            String errorGroup = iyzicoUtil.executeTransaction(incomingTransaction);
            if (errorGroup == null) {
                wallet.executeTransaction(incomingTransaction.toTransaction());
                walletRepository.save(wallet);
            } else {
                switch (errorGroup) {
                    case "NOT_SUFFICIENT_FUNDS" -> errorGroup = "Not enough funds in the card";
                    case "EXPIRED_CARD" -> errorGroup = "Card is expired";
                    case "LOST_CARD" -> errorGroup = "Card is lost";
                    case "STOLEN_CARD" -> errorGroup = "Card is stolen";
                    case "INVALID_CVC2" -> errorGroup = "Invalid CVC2 code";
                    case "NOT_PERMITTED_TO_CARDHOLDER" -> errorGroup = "Transaction is not permitted to cardholder";
                    case "FRAUD_SUSPECT" -> errorGroup = "Fraud suspect";
                    default -> errorGroup = "Transaction is not completed";
                }
                throw new TransactionNotValidatedException(errorGroup);
            }
        }
        else if(incomingTransaction.getTransactionType().equals(TransactionTypes.ADVERTISEMENT)) {
            wallet.executeTransaction(incomingTransaction.toTransaction());
            walletRepository.save(wallet);
        }
        else {
            throw new TransactionNotValidatedException("Transaction type is not valid");
        }
        return new WalletDTO(wallet);

    }
    public List<TransactionDTO> getAllTransactions(int walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet with id " + walletId + " is not found.");
        }
        ArrayList<TransactionDTO> transactions = new ArrayList<>();
        for(Transaction t : wallet.getTransactions()) {
            transactions.add(new TransactionDTO(t));
        }
        return transactions;
    }
    public TransactionDTO getTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction with id " + id + " is not found.");
        }
        return new TransactionDTO(transaction);
    }
    public List<TransactionDTO> getTransactionsByDate(String startDate, String endDate, int walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet with id " + walletId + " is not found.");
        }
        List<Transaction> transactions = wallet.getTransactions().stream().toList();
        List<Transaction> tmp = new ArrayList<>();
        // Filter transactions by date
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionDate().compareTo(startDate) >= 0 && transaction.getTransactionDate().compareTo(endDate) <= 0) {
                tmp.add(transaction);
            }
        }
        ArrayList<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(Transaction t : tmp) {
            transactionDTOS.add(new TransactionDTO(t));
        }
        return transactionDTOS;
    }
}

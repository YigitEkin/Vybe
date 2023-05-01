package com.vybe.backend.service;

import com.vybe.backend.exception.TransactionNotFoundException;
import com.vybe.backend.exception.WalletNotFoundException;
import com.vybe.backend.model.dto.IncomingTransactionDTO;
import com.vybe.backend.model.dto.TransactionDTO;
import com.vybe.backend.model.dto.WalletDTO;
import com.vybe.backend.model.entity.Transaction;
import com.vybe.backend.model.entity.Wallet;
import com.vybe.backend.repository.TransactionRepository;
import com.vybe.backend.repository.WalletRepository;
import com.vybe.backend.util.IyzicoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Resource
    WalletRepository walletRepository;
    @Resource
    TransactionRepository transactionRepository;
    @Resource
    IyzicoUtil iyzicoUtil;

    @Autowired
    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public WalletDTO executeNewTransaction(IncomingTransactionDTO incomingTransaction, Integer walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet with id " + walletId + " is not found.");
        }
        iyzicoUtil.executeTransaction(incomingTransaction);
        wallet.executeTransaction(incomingTransaction.toTransaction());
        walletRepository.save(wallet);
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
        List<Transaction> transactions = wallet.getTransactions();
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

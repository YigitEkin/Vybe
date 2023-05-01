package com.vybe.backend.controller;

import com.vybe.backend.model.dto.IncomingTransactionDTO;
import com.vybe.backend.model.dto.TransactionDTO;
import com.vybe.backend.model.dto.WalletDTO;
import com.vybe.backend.service.TransactionService;
import com.vybe.backend.util.IyzicoUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    @Resource
    public TransactionService transactionService;

    @PostMapping("/{wallet}")
    public WalletDTO newTransaction(@PathVariable Integer wallet, @RequestBody IncomingTransactionDTO transaction) {
        return transactionService.executeNewTransaction(transaction, wallet);
    }
    @GetMapping("/id")
    public TransactionDTO getById( @RequestParam Integer id) {
        return transactionService.getTransaction(id);
    }
    @GetMapping("/wallet")
    public List<TransactionDTO> getAll(@RequestParam Integer id) {
        return transactionService.getAllTransactions(id);
    }
    @GetMapping("/byDate")
    public List<TransactionDTO> getByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Integer id) {
        return transactionService.getTransactionsByDate(startDate, endDate, id);
    }
}

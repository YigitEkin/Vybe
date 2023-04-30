package com.vybe.backend.service;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.model.dto.WalletDTO;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.TransactionRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WalletService {
    @Resource
    CustomerRepository customerRepository;

    @Autowired
    public WalletService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public WalletDTO getWallet(String username) {
        Customer tmp =  customerRepository.findByUsername(username);
        if (tmp == null) {
            throw new CustomerNotFoundException("Customer with username: " + username + " is not found");
        }
        return new WalletDTO(tmp.getWallet());
    }
}

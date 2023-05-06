package com.vybe.backend.controller;

import com.vybe.backend.model.dto.WalletDTO;
import com.vybe.backend.service.WalletService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")

public class WalletController {
    @Resource
    public WalletService walletService;
    @GetMapping
    public WalletDTO getWallet(@RequestParam String username) {
        return walletService.getWallet(username);
    }
}

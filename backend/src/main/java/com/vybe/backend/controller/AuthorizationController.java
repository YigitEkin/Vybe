package com.vybe.backend.controller;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthorizationController {

    @Resource
    private AuthService authService;

    @GetMapping("/signIn")
    public Boolean authorizeUser(@RequestBody SignInDTO signInDTO ) {
            return authService.authorizeUsernameAndPassword(signInDTO.getUsername(), signInDTO.getPassword());
    }

    @GetMapping("/customer/2FA")
    public CustomerDTO signInCustomer2FA(@RequestBody SignInDTO signInDTO) {
        return authService.authorizeCustomer2FA(signInDTO.getCode(),signInDTO.getUsername());
    }

    @GetMapping("/venueAdmin/2FA")
    public VenueAdminDTO signInVenueAdmin2FA(@RequestBody SignInDTO signInDTO) {
        return authService.authorizeVenueAdmin2FA(signInDTO.getCode(),signInDTO.getUsername());
    }

    @PostMapping("/customer")
    public CustomerDTO registerCustomer(@RequestBody CustomerCreationDTO customerCreationDTO) {
        return authService.registerCustomer(customerCreationDTO, customerCreationDTO.getCode());
    }

    @PostMapping("/venueAdmin")
    public VenueAdminDTO registerVenueAdmin(@RequestBody VenueAdminCreationDTO venueAdminCreationDTO) {
        return authService.registerVenueAdmin(venueAdminCreationDTO, venueAdminCreationDTO.getCode());
    }

    @GetMapping("/2FA")
    public Boolean send2FA(@RequestParam String phoneNumber) {
        return authService.send2FA("+" + phoneNumber);
    }



}

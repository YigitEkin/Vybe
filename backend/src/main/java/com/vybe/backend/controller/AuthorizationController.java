package com.vybe.backend.controller;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.service.AuthService;
import com.vybe.backend.util.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class AuthorizationController {

    @Resource
    private AuthService authService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/signIn")
    public String authorizeUser(@RequestBody SignInDTO signInDTO ) {
            if(authService.authorizeUsernameAndPassword(signInDTO.getUsername(), signInDTO.getPassword()))
                return jwtTokenUtil.generateJwtToken(signInDTO.getUsername());
            else
                throw new CustomerNotFoundException("Username or password is incorrect");
    }

    @PostMapping("/customer/2FA")
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

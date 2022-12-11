package com.vybe.backend.service;

import com.vybe.backend.repository.AdminRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.VenueAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    CustomerRepository customerRepository;
    VenueAdminRepository venueAdminRepository;
    AdminRepository adminRepository;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository, VenueAdminRepository venueAdminRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.venueAdminRepository = venueAdminRepository;
        this.adminRepository = adminRepository;
    }
}

package com.vybe.backend.service;

import com.vybe.backend.DTO.*;
import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.UsernameTakenException;
import com.vybe.backend.repository.AdminRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.VenueAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    // ********** CUSTOMER **********

    public CustomerDTO addCustomer(CustomerCreationDTO customerCreationDTO) {
        if(userRepository.existsByUsername(customerCreationDTO.getUsername())) {
            throw new UsernameTakenException("Username already exists");
        }

        // TODO: should we check if the phone number exists?
        // TODO: hash the password
        return new CustomerDTO(customerRepository.save(customerCreationDTO.toCustomer()));
    }

    public CustomerDTO getCustomer(String username) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return new CustomerDTO(customerRepository.findByUsername(username));
    }

    public CustomerDTO updateCustomer(CustomerCreationDTO customerCreationDTO) {
        if (!customerRepository.existsByUsername(customerCreationDTO.getUsername())) {
            throw new CustomerNotFoundException("Customer with username: " + customerCreationDTO.getUsername() + " not found");
        }
        return  new CustomerDTO(customerRepository.save(customerCreationDTO.toCustomer()));
    }

    public void deleteCustomer(String username) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        customerRepository.deleteByUsername(username);
    }


    // ********** VENUE ADMIN **********
    public VenueAdminDTO addVenueAdmin(VenueAdminCreationDTO venueAdminCreationDTO) {
        if (userRepository.existsByUsername(venueAdminCreationDTO.getUsername())) {
            throw new UsernameTakenException("Username already exists");
        }
        // TODO: should we check if the phone number exists?
        // TODO: hash the password
        return new VenueAdminDTO(venueAdminRepository.save(venueAdminCreationDTO.toVenueAdmin()));
    }

    public VenueAdminDTO getVenueAdmin(String username) {
        if (!venueAdminRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Venue Admin with username: " + username + " not found");
        }
        return new VenueAdminDTO(venueAdminRepository.findByUsername(username));
    }

    public VenueAdminDTO updateVenueAdmin(VenueAdminCreationDTO venueAdminCreationDTO) {
        if (!venueAdminRepository.existsByUsername(venueAdminCreationDTO.getUsername())) {
            throw new CustomerNotFoundException("Venue Admin with username: " + venueAdminCreationDTO.getUsername() + " not found");
        }
        return new VenueAdminDTO(venueAdminRepository.save(venueAdminCreationDTO.toVenueAdmin()));
    }

    public void deleteVenueAdmin(String username) {
        if (!venueAdminRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Venue Admin with username: " + username + " not found");
        }
        venueAdminRepository.deleteByUsername(username);
    }

    // ********** ADMIN **********
    public AdminDTO addAdmin(AdminCreationDTO adminCreationDTO) {
        if (userRepository.existsByUsername(adminCreationDTO.getUsername())) {
            throw new UsernameTakenException("Username already exists");
        }
        // TODO: hash the password
        return new AdminDTO(adminRepository.save(adminCreationDTO.toAdmin()));
    }

    public AdminDTO getAdmin(String username) {
        if (!adminRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Admin with username: " + username + " not found");
        }
        return new AdminDTO(adminRepository.findByUsername(username));
    }

    public AdminDTO updateAdmin(AdminCreationDTO adminCreationDTO) {
        if (!adminRepository.existsByUsername(adminCreationDTO.getUsername())) {
            throw new CustomerNotFoundException("Admin with username: " + adminCreationDTO.getUsername() + " not found");
        }
        return new AdminDTO(adminRepository.save(adminCreationDTO.toAdmin()));
    }

    public void deleteAdmin(String username) {
        if (!adminRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Admin with username: " + username + " not found");
        }
        adminRepository.deleteByUsername(username);
    }


}

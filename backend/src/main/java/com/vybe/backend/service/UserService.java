package com.vybe.backend.service;

import com.vybe.backend.exception.*;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.User;
import com.vybe.backend.repository.AdminRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.VenueAdminRepository;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
        if (userRepository.existsByPhoneNumber(customerCreationDTO.getPhoneNumber())) {
            throw new PhoneNumberTakenException("Phone number already exists");
        }
        // TODO: hash the password
        return new CustomerDTO(customerRepository.save(customerCreationDTO.toCustomer()));
    }

    public User authorizeCustomer(String username, String password) throws InvalidCredentialsException {
        return userRepository.getUserByUsernameAndPassword(username, password)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
    }

    // get all customers
    public Iterable<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerDTO::new).collect(Collectors.toList());
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
        if (userRepository.existsByPhoneNumber(venueAdminCreationDTO.getPhoneNumber())) {
            throw new PhoneNumberTakenException("Phone number already exists");
        }
        // TODO: hash the password
        return new VenueAdminDTO(venueAdminRepository.save(venueAdminCreationDTO.toVenueAdmin()));
    }

    // get all venue admins
    public Iterable<VenueAdminDTO> getAllVenueAdmins() {
        return venueAdminRepository.findAll().stream().map(VenueAdminDTO::new).collect(Collectors.toList());
    }

    public VenueAdminDTO getVenueAdmin(String username) {
        if (!venueAdminRepository.existsByUsername(username)) {
            throw new VenueAdminNotFoundException("Venue Admin with username: " + username + " not found");
        }
        return new VenueAdminDTO(venueAdminRepository.findByUsername(username));
    }

    public VenueAdminDTO updateVenueAdmin(VenueAdminCreationDTO venueAdminCreationDTO) {
        if (!venueAdminRepository.existsByUsername(venueAdminCreationDTO.getUsername())) {
            throw new VenueAdminNotFoundException("Venue Admin with username: " + venueAdminCreationDTO.getUsername() + " not found");
        }
        return new VenueAdminDTO(venueAdminRepository.save(venueAdminCreationDTO.toVenueAdmin()));
    }

    public void deleteVenueAdmin(String username) {
        if (!venueAdminRepository.existsByUsername(username)) {
            throw new VenueAdminNotFoundException("Venue Admin with username: " + username + " not found");
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
            throw new AdminNotFoundException("Admin with username: " + username + " not found");
        }
        return new AdminDTO(adminRepository.findByUsername(username));
    }

    public Iterable<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream().map(AdminDTO::new).collect(Collectors.toList());
    }

    public AdminDTO updateAdmin(AdminCreationDTO adminCreationDTO) {
        if (!adminRepository.existsByUsername(adminCreationDTO.getUsername())) {
            throw new AdminNotFoundException("Admin with username: " + adminCreationDTO.getUsername() + " not found");
        }
        return new AdminDTO(adminRepository.save(adminCreationDTO.toAdmin()));
    }

    public void deleteAdmin(String username) {
        if (!adminRepository.existsByUsername(username)) {
            throw new AdminNotFoundException("Admin with username: " + username + " not found");
        }
        adminRepository.deleteByUsername(username);
    }


}

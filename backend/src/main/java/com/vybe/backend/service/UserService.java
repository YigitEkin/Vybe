package com.vybe.backend.service;

import com.vybe.backend.exception.*;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.*;
import com.vybe.backend.repository.*;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;
    CustomerRepository customerRepository;
    VenueAdminRepository venueAdminRepository;
    AdminRepository adminRepository;
    VenueRepository venueRepository;
    StreakRepository streakRepository;
    VisitRepository visitRepository;

    @Autowired
    public UserService(UserRepository userRepository, CustomerRepository customerRepository, VenueAdminRepository venueAdminRepository, AdminRepository adminRepository, VenueRepository venueRepository, StreakRepository streakRepository, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.venueAdminRepository = venueAdminRepository;
        this.adminRepository = adminRepository;
        this.venueRepository = venueRepository;
        this.streakRepository = streakRepository;
        this.visitRepository = visitRepository;
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

    public boolean deleteCustomer(String username) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return customerRepository.deleteByUsername(username) > 0;
    }

    // ********** Check in/out Methods **********
    public String checkIn(String customer_username, Integer venue_id) {
        if (!customerRepository.existsByUsername(customer_username)) {
            throw new CustomerNotFoundException("Customer with username: " + customer_username + " not found");
        }
        if (!venueRepository.existsById(venue_id)) {
            throw new VenueNotFoundException("Venue with id: " + venue_id + " not found");
        }
        Venue venue = venueRepository.findById(venue_id).get();
        Customer customer = customerRepository.findByUsername(customer_username);
        if (customer.getCheckedInVenue() != null) {
            throw new AlreadyCheckedInException("Customer with username: " + customer_username + " is already checked in venue with id: " + customer.getCheckedInVenue().getId());
        }
        if (venue.getCheckedInCustomers().contains(customer)) {
            throw new AlreadyCheckedInException("Customer with username: " + customer_username + " is already checked in venue with id: " + venue_id);
        }
        // might comment
        updateStreak(customer_username, venue_id);

        // add visit
        Visit visit = new Visit(0, customer_username, venue_id, venue.getName(), new Date());
        visitRepository.save(visit);

        venue.getCheckedInCustomers().add(customer);
        customer.setCheckedInVenue(venue);
        return "Customer with username: " + customer_username + " checked in venue with id: " + venue_id + " and name " + venue.getName();
    }

    public String checkOut(String customer_username, Integer venue_id) {
        if (!customerRepository.existsByUsername(customer_username)) {
            throw new CustomerNotFoundException("Customer with username: " + customer_username + " not found");
        }
        if (!venueRepository.existsById(venue_id)) {
            throw new VenueNotFoundException("Venue with id: " + venue_id + " not found");
        }
        Venue venue = venueRepository.findById(venue_id).get();
        Customer customer = customerRepository.findByUsername(customer_username);
        if (customer.getCheckedInVenue() == null) {
            throw new NotCheckedInException("Customer with username: " + customer_username + " is not checked in");
        }
        if (!venue.getCheckedInCustomers().contains(customer)) {
            throw new NotCheckedInException("Customer with username: " + customer_username + " is not checked in venue with id: " + venue_id);
        }
        venue.getCheckedInCustomers().remove(customer);
        customer.setCheckedInVenue(null);
        return "Customer with username: " + customer_username + " checked out of venue with id: " + venue_id + " and name " + venue.getName();
    }

    // ********** Streak Methods **********
    // get specific streak
    public StreakDTO getStreak(String customer_username, Integer venue_id) {
        if (!customerRepository.existsByUsername(customer_username)) {
            throw new CustomerNotFoundException("Customer with username: " + customer_username + " not found");
        }
        if (!venueRepository.existsById(venue_id)) {
            throw new VenueNotFoundException("Venue with id: " + venue_id + " not found");
        }
        Streak streak = streakRepository.findByCustomerUsernameAndVenueId(customer_username, venue_id)
                .orElseThrow(() -> new StreakNotFoundException("Streak of customer with username: " + customer_username + " at venue with id: " + venue_id + " not found"));
        return new StreakDTO(streak);

    }

    // get all streaks
    public Iterable<StreakDTO> getAllStreaks() {
        return streakRepository.findAll().stream().map(StreakDTO::new).collect(Collectors.toList());
    }

    // Calculate/update streak
    public StreakDTO updateStreak(String customer_username, Integer venue_id) {
        if (!customerRepository.existsByUsername(customer_username)) {
            throw new CustomerNotFoundException("Customer with username: " + customer_username + " not found");
        }
        if (!venueRepository.existsById(venue_id)) {
            throw new VenueNotFoundException("Venue with id: " + venue_id + " not found");
        }
        Streak streak = streakRepository.findByCustomerUsernameAndVenueId(customer_username, venue_id).orElse(null);

        if(streak == null){
            streak = new Streak();
            streak.setId(0);
            streak.setCustomer(customerRepository.findByUsername(customer_username));
            streak.setVenue(venueRepository.findById(venue_id).get());
            streak.setLastVisitDate(new Date());
            streak.setStreak(1);
        } else {
            Date today = new Date();
            Date lastStreak = streak.getLastVisitDate();
            // if last visit was between 24 and 48 hours ago (inclusive) then increment streak
            if (today.getTime() - lastStreak.getTime() <= 172800000 && today.getTime() - lastStreak.getTime() >= 86400000) {
                streak.setStreak(streak.getStreak() + 1);
                streak.setLastVisitDate(today);
            }
            // if last visit was more than 48 hours ago then reset streak
            else if (today.getTime() - lastStreak.getTime() > 172800000) {
                streak.setStreak(1);
                streak.setLastVisitDate(today);
            }

        }
        return new StreakDTO(streakRepository.save((streak)));
    }

    public List<StreakDTO> getAllStreaks(String customer_username) {
        if (!customerRepository.existsByUsername(customer_username)) {
            throw new CustomerNotFoundException("Customer with username: " + customer_username + " not found");
        }
        List<Streak> streaks = streakRepository.findAllByCustomerUsername(customer_username);
        return streaks.stream().map(StreakDTO::new).collect(Collectors.toList());
    }

    // ********** VENUE ADMIN **********
    public VenueAdminDTO addVenueAdmin(VenueAdminCreationDTO venueAdminCreationDTO) {
        if (userRepository.existsByUsername(venueAdminCreationDTO.getUsername()))
            throw new UsernameTakenException("Username already exists");
        if (userRepository.existsByPhoneNumber(venueAdminCreationDTO.getPhoneNumber()))
            throw new PhoneNumberTakenException("Phone number already exists");
        if (!venueRepository.existsByName(venueAdminCreationDTO.getVenueName())) 
            throw new VenueNotFoundException("Venue with name: " + venueAdminCreationDTO.getVenueName() + " not found");

        VenueAdmin venueAdmin = venueAdminCreationDTO.toVenueAdmin();
        venueAdmin.setVenue(venueRepository.findByName(venueAdminCreationDTO.getVenueName()));
        
        return new VenueAdminDTO(venueAdminRepository.save(venueAdmin));
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

    public boolean uploadCustomerPhoto(String username, ImageDTO data) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        Customer customer = customerRepository.findByUsername(username);
        Image image = customer.getProfilePicture();
        if (image == null) {
            image = new Image();
        }
        image.setData(data.getImage());
        customer.setProfilePicture(image);
        customerRepository.save(customer);
        return true;
    }

    public boolean deleteCustomerPhoto(String username) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        Customer customer = customerRepository.findByUsername(username);
        Image image = customer.getProfilePicture();
        if (image == null) {
            return false;
        }
        customer.setProfilePicture(null);
        customerRepository.save(customer);
        return true;
    }

    public boolean uploadVenueAdminImage(String username, String data) {
        if (!venueAdminRepository.existsByUsername(username)) {
            throw new VenueAdminNotFoundException("Venue admin with username: " + username + " not found");
        }
        VenueAdmin venueAdmin = venueAdminRepository.findByUsername(username);
        Image image = venueAdmin.getProfilePicture();
        if (image == null) {
            image = new Image();
        }
        image.setData(data);
        venueAdmin.setProfilePicture(image);
        venueAdminRepository.save(venueAdmin);
        return true;
    }

    public ImageDTO getCustomerPhoto(String username) {
        if (!customerRepository.existsByUsername(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        Customer customer = customerRepository.findByUsername(username);
        Image image = customer.getProfilePicture();
        if (image == null) {
            return null;
        }
        return new ImageDTO(image);
    }

}

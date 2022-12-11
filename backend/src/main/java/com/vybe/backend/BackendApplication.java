package com.vybe.backend;

import com.vybe.backend.DTO.*;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.User;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.service.UserService;
import com.vybe.backend.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	UserRepository userRepository;
	CustomerRepository customerRepository;
	SongRepository songRepository;
	UserService userService;
	VenueService venueService;

	@Autowired
	public BackendApplication(UserRepository userRepository, CustomerRepository customerRepository, SongRepository songRepository, UserService userService, VenueService venueService) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.songRepository = songRepository;
		this.userService = userService;
		this.venueService = venueService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner lineRunner(CustomerRepository customerRepository, SongRepository songRepository, UserRepository userRepository,
										UserService userService, VenueService venueService) {
		return args -> {
			// test user service class using assert statements to check if the methods work
			CustomerCreationDTO customerCreationDTO = new CustomerCreationDTO("testmail1", "testpass1", "testname1", "testphone1", "testdate1", "testdate1");
			VenueAdminCreationDTO venueAdminCreationDTO = new VenueAdminCreationDTO("testname3", "testpass3", "testmail3", "testphone3");
			AdminCreationDTO adminCreationDTO = new AdminCreationDTO("testname5", "testpass5");

			System.out.println("Testing");

			// test create customer
			CustomerDTO customerDTO = userService.addCustomer(customerCreationDTO);
			assert customerDTO.getUsername().equals("testname1");
			assert customerDTO.getEmail().equals("testmail1");
			assert customerDTO.getPhoneNumber().equals("testphone1");
			assert customerDTO.getDateOfBirth().equals("testdate1");
			assert customerDTO.getDateOfCreation().equals("testdate1");

			// test create venue admin
			VenueAdminDTO venueAdminDTO = userService.addVenueAdmin(venueAdminCreationDTO);
			assert venueAdminDTO.getUsername().equals("testname3");
			assert venueAdminDTO.getEmail().equals("testmail3");
			assert venueAdminDTO.getPhoneNumber().equals("testphone3");

			// test create admin
			AdminDTO adminDTO = userService.addAdmin(adminCreationDTO);
			assert adminDTO.getUsername().equals("testname5");

			// test get customer
			CustomerDTO customerDTO2 = userService.getCustomer("testname1");
			assert customerDTO2.getUsername().equals("testname1");
			assert customerDTO2.getEmail().equals("testmail1");
			assert customerDTO2.getPhoneNumber().equals("testphone1");
			assert customerDTO2.getDateOfBirth().equals("testdate1");
			assert customerDTO2.getDateOfCreation().equals("testdate1");

			// test get venue admin
			VenueAdminDTO venueAdminDTO2 = userService.getVenueAdmin("testname3");
			assert venueAdminDTO2.getUsername().equals("testname3");
			assert venueAdminDTO2.getEmail().equals("testmail3");
			assert venueAdminDTO2.getPhoneNumber().equals("testphone3");

			// test get admin
			AdminDTO adminDTO2 = userService.getAdmin("testname5");
			assert adminDTO2.getUsername().equals("testname5");

			// test adding a customer with an existing username
			CustomerCreationDTO customerCreationDTO3 = new CustomerCreationDTO("testmail1", "testpass1", "testname1", "testphone1", "testdate1", "testgender1");
			try {
				CustomerDTO customerDTO3 = userService.addCustomer(customerCreationDTO3);
			} catch (Exception e) {
				assert e.getMessage().equals("Username already exists");
			}

			// test adding a venue admin with an existing username
			VenueAdminCreationDTO venueAdminCreationDTO3 = new VenueAdminCreationDTO("testname3", "testpass3", "testmail3", "testphone3");
			try {
				VenueAdminDTO venueAdminDTO3 = userService.addVenueAdmin(venueAdminCreationDTO3);
			} catch (Exception e) {
				assert e.getMessage().equals("Username already exists");
			}

			// test adding an admin with an existing username
			AdminCreationDTO adminCreationDTO3 = new AdminCreationDTO("testname5", "testpass5");
			try {
				AdminDTO adminDTO3 = userService.addAdmin(adminCreationDTO3);
			} catch (Exception e) {
				assert e.getMessage().equals("Username already exists");
			}

			// test deleting a customer
			userService.deleteCustomer("testname1");
			try {
				CustomerDTO customerDTO4 = userService.getCustomer("testname1");
			} catch (Exception e) {
				assert e.getMessage().equals("Customer with username: testname1 not found");
			}

			// test deleting a venue admin
			userService.deleteVenueAdmin("testname3");
			try {
				VenueAdminDTO venueAdminDTO4 = userService.getVenueAdmin("testname3");
			} catch (Exception e) {
				assert e.getMessage().equals("Venue Admin with username: testname3 not found");
			}

			// test deleting an admin
			userService.deleteAdmin("testname5");
			try {
				AdminDTO adminDTO4 = userService.getAdmin("testname5");
			} catch (Exception e) {
				assert e.getMessage().equals("Admin with username: testname5 not found");
			}

			// test adding a venue
			VenueCreationDTO venueCreationDTO = new VenueCreationDTO("testname1", "testdescription1", "testlocation1");
			VenueDTO venueDTO = venueService.addVenue(venueCreationDTO);
			assert venueDTO.getName().equals("testname1");
			assert venueDTO.getDescription().equals("testdescription1");
			assert venueDTO.getLocation().equals("testlocation1");

			// test getting a venue
			VenueDTO venueDTO2 = venueService.getVenue("testname1");
			assert venueDTO2.getName().equals("testname1");

			// test getting a venue with an id
			VenueDTO venueDTO3 = venueService.getVenue(venueDTO.getId());
			assert venueDTO3.getName().equals("testname1");

			// test getting a vennue with a non-existent id
			try {
				VenueDTO venueDTO4 = venueService.getVenue(100);
			} catch (Exception e) {
				assert e.getMessage().equals("Venue with id: 100 not found");
			}

			// test getting a venue with a non-existent name
			try {
				VenueDTO venueDTO5 = venueService.getVenue("testname100");
			} catch (Exception e) {
				assert e.getMessage().equals("Venue with name: testname100 not found");
			}

			// test deleting a venue by id
			venueService.deleteVenue(venueDTO.getId());
			try {
				VenueDTO venueDTO6 = venueService.getVenue(venueDTO.getId());
			} catch (Exception e) {
				assert e.getMessage().equals("Venue with id: " + venueDTO.getId() + " not found");
			}

			System.out.println("Tests passed");


		};
	}

}

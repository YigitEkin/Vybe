package com.vybe.backend;

import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.User;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.UserRepository;
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

	@Autowired
	public BackendApplication(UserRepository userRepository, CustomerRepository customerRepository, SongRepository songRepository) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.songRepository = songRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

	}

	@Bean
	public CommandLineRunner lineRunner(CustomerRepository customerRepository, SongRepository songRepository, UserRepository userRepository) {
		return args -> {
			// create a new user in a single line according to the User class constructor
			userRepository.save(new User("username1", "testpassword", "email", "phonenumber", null, null, null));
			customerRepository.save(new Customer("username2", "testpassword", "email", "phonenumber", null, null, null, null, "02.02.2002", "08.12.2022"));


		};
	}

}

package com.vybe.backend;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.service.PlaylistService;
import com.vybe.backend.service.SongService;
import com.vybe.backend.service.UserService;
import com.vybe.backend.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BackendApplication {

	UserRepository userRepository;
	CustomerRepository customerRepository;
	SongRepository songRepository;
	UserService userService;
	VenueService venueService;
	SongService songService;
	PlaylistService playlistService;

	@Autowired
	public BackendApplication(UserRepository userRepository, CustomerRepository customerRepository, SongRepository songRepository,
							  UserService userService, VenueService venueService, SongService songService, PlaylistService playlistService) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.songRepository = songRepository;
		this.userService = userService;
		this.venueService = venueService;
		this.songService = songService;
		this.playlistService = playlistService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner lineRunner(CustomerRepository customerRepository, SongRepository songRepository, UserRepository userRepository,
										UserService userService, VenueService venueService, SongService songService, PlaylistService playlistService) {
		return args -> {
			// test user service class using assert statements to check if the methods work
			CustomerCreationDTO customerCreationDTO = new CustomerCreationDTO( "testpass1", "testname1", "testphone1", "testdate1", "testdate1", "000000");
			VenueAdminCreationDTO venueAdminCreationDTO = new VenueAdminCreationDTO("testname3", "testpass3", "testmail3", "testphone3");
			AdminCreationDTO adminCreationDTO = new AdminCreationDTO("testname5", "testpass5");

			System.out.println("Testing");

			// test create customer
			CustomerDTO customerDTO = userService.addCustomer(customerCreationDTO);
			assert customerDTO.getUsername().equals("testname1");
			assert customerDTO.getPhoneNumber().equals("testphone1");
			assert customerDTO.getDateOfBirth().equals("testdate1");
			assert customerDTO.getDateOfCreation().equals("testdate1");

			// test create venue admin
			VenueAdminDTO venueAdminDTO = userService.addVenueAdmin(venueAdminCreationDTO);
			assert venueAdminDTO.getUsername().equals("testname3");
			assert venueAdminDTO.getPhoneNumber().equals("testphone3");

			// test create admin
			AdminDTO adminDTO = userService.addAdmin(adminCreationDTO);
			assert adminDTO.getUsername().equals("testname5");

			// TODO: test phone number exception

			// test get customer
			CustomerDTO customerDTO2 = userService.getCustomer("testname1");
			assert customerDTO2.getUsername().equals("testname1");
			assert customerDTO2.getPhoneNumber().equals("testphone1");
			assert customerDTO2.getDateOfBirth().equals("testdate1");
			assert customerDTO2.getDateOfCreation().equals("testdate1");

			// test get venue admin
			VenueAdminDTO venueAdminDTO2 = userService.getVenueAdmin("testname3");
			assert venueAdminDTO2.getUsername().equals("testname3");
			assert venueAdminDTO2.getPhoneNumber().equals("testphone3");

			// test get admin
			AdminDTO adminDTO2 = userService.getAdmin("testname5");
			assert adminDTO2.getUsername().equals("testname5");

			// test adding a customer with an existing username
			CustomerCreationDTO customerCreationDTO3 = new CustomerCreationDTO( "testpass1", "testname1", "testphone1", "testdate1", "testgender1", "000000");
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
			VenueCreationDTO venueCreationDTO = new VenueCreationDTO("testname1", "testdescription1", "testlocation1", "testsoundzoneID");
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

			// test adding a song
			SongDTO songCreationDTO = new SongDTO("0", "testname1", "testartist1", "testart1", "testlink1", "testsoundtrackYBId");
			SongDTO songDTO = songService.addSong(songCreationDTO);
			assert songDTO.getName().equals("testname1");
			assert songDTO.getArtist().equals("testartist1");
			assert songDTO.getAlbumArt().equals("testart1");
			assert songDTO.getLink().equals("testlink1");

			// test getting a song
			SongDTO songDTO2 = songService.getSongByName("testname1");
			assert songDTO2.getName().equals("testname1");

			// test getting a song with an id
			SongDTO songDTO3 = songService.getSong(songDTO.getId());
			assert songDTO3.getName().equals("testname1");

			// test getting a song with a non-existent id
			try {
				SongDTO songDTO4 = songService.getSong("100");
			} catch (Exception e) {
				assert e.getMessage().equals("Song with id: 100 not found");
			}

			// test getting a song with a non-existent name
			try {
				SongDTO songDTO5 = songService.getSongByName("testname100");
			} catch (Exception e) {
				assert e.getMessage().equals("Song with name: testname100 not found");
			}

			// test deleting a song by id
			songService.deleteSong(songDTO.getId());
			try {
				SongDTO songDTO6 = songService.getSong(songDTO.getId());
			} catch (Exception e) {
				assert e.getMessage().equals("Song with id: " + songDTO.getId() + " not found");
			}

			// test adding a playlist
			VenueCreationDTO venueCreationDTO2 = new VenueCreationDTO("testname2", "testdescription2", "testlocation2", "testsoundzoneID");
			venueDTO2 = venueService.addVenue(venueCreationDTO2);
			PlaylistCreationDTO playlistCreationDTO = new PlaylistCreationDTO(venueDTO2.getId(), "defaultplaylistid", "requestplaylistid");

			VenueDTO venueDTO4 = venueService.getVenue(venueDTO2.getId());
			assert venueDTO4.getName().equals("testname2");

			PlaylistDTO playlistDTO = playlistService.createPlaylist(playlistCreationDTO);
			assert playlistDTO.getVenueId() == venueDTO2.getId();



			// test getting a playlist
			PlaylistDTO playlistDTO2 = playlistService.getPlaylist(playlistDTO.getId());
			assert playlistDTO2.getVenueId() == venueDTO2.getId();

			// test getting a playlist with a non-existent id
			try {
				PlaylistDTO playlistDTO3 = playlistService.getPlaylist(100);
			} catch (Exception e) {
				assert e.getMessage().equals("Playlist with id: 100 not found");
			}

			// test getting playlist by venue id
			PlaylistDTO playlistDTO4 = playlistService.getPlaylistOfVenue(venueDTO2.getId());
			assert playlistDTO4.getVenueId() == venueDTO2.getId();

			// test getting playlist by non-existent venue id
			try {
				PlaylistDTO playlistDTO5 = playlistService.getPlaylistOfVenue(100);
			} catch (Exception e) {
				assert e.getMessage().equals("Playlist of venue with id: 100 not found");
			}

			// test the playlist from the venue
			VenueDTO venueDTO5 = venueService.getVenue(venueDTO2.getId());
			assert venueDTO5.getPlaylist().getId().equals(playlistDTO4.getId());

			// test adding banned genres to a playlist
			playlistService.addBannedGenresToPlaylist(playlistDTO.getId(), Arrays.asList("testgenre1", "testgenre2"));
			PlaylistDTO playlistDTO6 = playlistService.getPlaylist(playlistDTO.getId());
			assert playlistDTO6.getBannedGenres().size() == 2;
			assert playlistDTO6.getBannedGenres().contains("testgenre1");
			assert playlistDTO6.getBannedGenres().contains("testgenre2");

			// test adding banned genres to a playlist with a non-existent id
			try {
				playlistService.addBannedGenresToPlaylist(100, Arrays.asList("testgenre1", "testgenre2"));
			} catch (Exception e) {
				assert e.getMessage().equals("Playlist with id: 100 not found");
			}

			// test adding banned genres to a playlist with a null list
			try {
				playlistService.addBannedGenresToPlaylist(playlistDTO.getId(), null);
			} catch (Exception e) {
				assert e.getMessage().equals("Banned genres list cannot be null");
			}

			// test to see if the banned genres are added to the playlist of the venue
			VenueDTO venueDTO6 = venueService.getVenue(venueDTO2.getId());
			assert venueDTO6.getPlaylist().getBannedGenres().size() == 2;
			assert venueDTO6.getPlaylist().getBannedGenres().contains("testgenre1");
			assert venueDTO6.getPlaylist().getBannedGenres().contains("testgenre2");

			// test deleting banned genres from a playlist
			playlistService.removeBannedGenresFromPlaylist(playlistDTO.getId(), Arrays.asList("testgenre1", "testgenre2"));
			PlaylistDTO playlistDTO7 = playlistService.getPlaylist(playlistDTO.getId());
			assert playlistDTO7.getBannedGenres().size() == 0;

			// test deleting banned genres from a playlist with a non-existent id
			try {
				playlistService.removeBannedGenresFromPlaylist(100, Arrays.asList("testgenre1", "testgenre2"));
			} catch (Exception e) {
				assert e.getMessage().equals("Playlist with id: 100 not found");
			}

			// test to see if the banned genres are deleted from the playlist of the venue
			VenueDTO venueDTO7 = venueService.getVenue(venueDTO2.getId());
			assert venueDTO7.getPlaylist().getBannedGenres().size() == 0;


			System.out.println("Tests passed");

			//TODO: add tests for setting the mode, and getting the mode
			//TODO: add tests for adding and removing songs from the default playlist
			//TODO: add tests for adding and removing songs from the request queue
		};
	}

}

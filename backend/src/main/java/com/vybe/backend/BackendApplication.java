package com.vybe.backend;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.enums.TransactionTypes;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.service.*;
import com.vybe.backend.util.IyzicoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BackendApplication {

	UserRepository userRepository;
	CustomerRepository customerRepository;
	SongRepository songRepository;
	UserService userService;
	VenueService venueService;
	SongService songService;
	PlaylistService playlistService;
	FriendshipService friendshipService;
	TransactionService transactionService;
	CommentService commentService;
	RatingService ratingService;

	@Autowired
	public BackendApplication(UserRepository userRepository, CustomerRepository customerRepository, SongRepository songRepository,
							  UserService userService, VenueService venueService, SongService songService, PlaylistService playlistService, FriendshipService friendshipService,TransactionService transactionService
							, CommentService commentService, RatingService ratingService) {
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
		this.songRepository = songRepository;
		this.userService = userService;
		this.venueService = venueService;
		this.songService = songService;
		this.playlistService = playlistService;
		this.friendshipService = friendshipService;
		this.transactionService = transactionService;
		this.commentService = commentService;
		this.ratingService = ratingService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner lineRunner(CustomerRepository customerRepository, SongRepository songRepository, UserRepository userRepository,
										UserService userService, VenueService venueService, SongService songService, PlaylistService playlistService, FriendshipService friendshipService, IyzicoUtil iyzicoUtil) {
		return args -> {
			VenueCreationDTO venueCreationDTO = new VenueCreationDTO("testname1", "testdescription1", "40.1, 20.23", "testtoken", "testsoundzoneID");
			VenueDTO venueDTO = venueService.addVenue(venueCreationDTO);

			// test user service class using assert statements to check if the methods work
			CustomerCreationDTO customerCreationDTO = new CustomerCreationDTO( "testpass1", "testname1","name", "surname", "testphone1", "testdate1", "testdate1", "000000");
			VenueAdminCreationDTO venueAdminCreationDTO = new VenueAdminCreationDTO("testname3", "name", "surname", "testpass3", "testmail3", "testphone3", "testname1");
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
			CustomerCreationDTO customerCreationDTO3 = new CustomerCreationDTO( "testpass1", "testname1", "name", "surname", "testphone1", "testdate1", "testgender1", "000000");
			try {
				CustomerDTO customerDTO3 = userService.addCustomer(customerCreationDTO3);
			} catch (Exception e) {
				assert e.getMessage().equals("Username already exists");
			}

			// test adding a venue admin with an existing username
			VenueAdminCreationDTO venueAdminCreationDTO3 = new VenueAdminCreationDTO("testname3", "name", "surname", "testpass3", "testmail3", "testphone3", "testname1");
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
			SongDTO songCreationDTO = new SongDTO(0, "testname1", "testartist1", "testart1", "testlink1");
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
				SongDTO songDTO4 = songService.getSong(100);
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
			VenueCreationDTO venueCreationDTO2 = new VenueCreationDTO("Bilkent Merkez Spor Salonu", "Üniversiteler, 1598. Cd., 06800 Çankaya/Ankara", "39.865644, 32.746131", "U291bmRab25lLCwxZXB1NDM3OGJuay9Mb2NhdGlvbiwsMWZlbHNmMGFkcTgvQWNjb3VudCwsMW96ZDN6dGk0ZzAv", "WkxnTVI0RW9YRWdPRWVsOEZ5TzZJZFBtSzFzOWpUQ0c6aVlxcVdIVXY3NGFLaXREQVFFQ2V3WmJHUHVxeVRNMlpybUo4Ulp3R3BQS2N3NEZpWGczT2FnUTFFVGtudGlPZQ==");
			venueDTO2 = venueService. addVenue(venueCreationDTO2);
			PlaylistCreationDTO playlistCreationDTO = new PlaylistCreationDTO(venueDTO2.getId(), "Q29sbGVjdGlvbiwsMW96YmE1anpzM2svU291bmRab25lLCwxZXB1NDM3OGJuay9Mb2NhdGlvbiwsMWZlbHNmMGFkcTgvQWNjb3VudCwsMW96ZDN6dGk0ZzAv", "Q29sbGVjdGlvbiwsMW96YmE1anpzM2svU291bmRab25lLCwxZXB1NDM3OGJuay9Mb2NhdGlvbiwsMWZlbHNmMGFkcTgvQWNjb3VudCwsMW96ZDN6dGk0ZzAv");

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

			// test adding songs
			SongDTO songCreationDTO1 = new SongDTO(0, "testname1", "testartist1", "testart1", "testlink1");
			SongDTO songCreationDTO2 = new SongDTO(2, "testname2", "testartist2", "testart2", "testlink2");
			SongDTO songCreationDTO3 = new SongDTO(3, "testname3", "testartist3", "testart3", "testlink3");
			SongDTO songCreatedDTO = songService.addSong(songCreationDTO1);
			SongDTO songCreatedDTO3 = songService.addSong(songCreationDTO3);

			assert songCreatedDTO.getName().equals("testname1");
			assert songCreatedDTO3.getName().equals("testname3");

			// test adding songs to a playlist
			playlistService.addSongToDefaultPlaylist(playlistDTO.getId(), songCreatedDTO.getId());

			assert playlistService.getPlaylist(playlistDTO.getId()).getDefaultPlaylist().size() == 2;
			assert playlistService.getPlaylist(playlistDTO.getId()).getDefaultPlaylist().contains(songCreatedDTO.toSong());


			// test get song from playlist
			//SongDTO songDTO12 = venueService.getNextSong(venueDTO2.getId());
			//System.out.println(songDTO12);

			//SongDTO songDTO13 = venueService.getNextSong(venueDTO2.getId());
			//System.out.println(songDTO13);

			// test adding songs to request playlist
			SongNodeDTO songNodeDTO1 = new SongNodeDTO(playlistDTO.getId(), songCreatedDTO.getId(), 1.0);

			//songService.addSongRequest(songNodeDTO1);
			//songService.addSongRequest(songNodeDTO2);
			//songService.addSongRequest(songNodeDTO1);
			//assert playlistService.getPlaylist(playlistDTO.getId()).getRequestedSongs().size() == 2;
			//System.out.println(playlistService.getPlaylist(playlistDTO.getId()).getRequestedSongs());

			// test getting next song from request playlist
			//ongDTO songDTO14 = venueService.getNextSong(venueDTO2.getId());
			//System.out.println(songDTO14);
			//assert songDTO14.getId().equals(songCreatedDTO.getId());

			//Song song = venueService.startSong(venueDTO2.getId());
			//System.out.println(song);



			// assert that the song is removed from the request playlist
			//assert playlistService.getPlaylist(playlistDTO.getId()).getRequestedSongs().size() == 1;

			playlistService.addAllSongsToDefaultPlaylist(playlistDTO.getId());

			// create song nodes
			SongNodeDTO songNodeDTO3 = new SongNodeDTO(playlistDTO.getId(), 17, 1.0);
			SongNodeDTO songNodeDTO4 = new SongNodeDTO(playlistDTO.getId(), 36, 1.0);
			SongNodeDTO songNodeDTO5 = new SongNodeDTO(playlistDTO.getId(), 17, 1.0);

			// add song nodes to request playlist
			//songService.addSongRequest(songNodeDTO3);
			//songService.addSongRequest(songNodeDTO4);
			//songService.addSongRequest(songNodeDTO5);

			//venueService.startSong(venueDTO2.getId());

			System.out.println("Tests passed");

			// ------------------------ Adding Dummy Data ------------------------
			// ------------------------ Customers ------------------------
			CustomerCreationDTO customerDTO1 = new CustomerCreationDTO("abc", "905076011168", "Oğuz Ata", "Çal", "+905076011168", "01.01.2001", "10.05.2023", null);
			CustomerCreationDTO customerDTO21 = new CustomerCreationDTO("abc","2", "Yiğit", "Ekin", "2", "01.01.2001", "10.05.2023", null);
			CustomerCreationDTO customerDTO3 = new CustomerCreationDTO("12345678", "905309510454", "Mehmet Berk", "Türkçapar", "+905309510454", "01.01.2001", "10.05.2023", null);
			CustomerCreationDTO customerDTO4 = new CustomerCreationDTO("abc","905332346981", "Harun Can", "Surav", "+905332346981", "01.01.2001", "10.05.2023", null);
			CustomerCreationDTO customerDTO5 = new CustomerCreationDTO("11111111", "905387866001", "Can", "Önal", "+905387866001", "01.01.2001", "10.05.2023", null);
			CustomerCreationDTO adminCustomer = new CustomerCreationDTO("1234","admin", "admin", "admin", "6", "01.01.2001", "10.05.2023", null);

			CustomerDTO customerResult1 = userService.addCustomer(customerDTO1);
			CustomerDTO customerResult2 = userService.addCustomer(customerDTO21);
			CustomerDTO customerResult3 = userService.addCustomer(customerDTO3);
			CustomerDTO customerResult4 = userService.addCustomer(customerDTO4);
			CustomerDTO customerResult5 = userService.addCustomer(customerDTO5);
			CustomerDTO customerResult6 = userService.addCustomer(adminCustomer);

			// ------------------------ Venues ------------------------
			VenueCreationDTO venueCreationDTO1 = new VenueCreationDTO("Fameo Cafe", "Üniversiteler, 1597. Cd. No:3 D:3, 06800 Çankaya/Ankara", "39.883241251952455, 32.75711146158583", "dummyZone", "dummyToken");
			venueCreationDTO2 = new VenueCreationDTO("Express Cafe", "Bilkent Üniversitesi merkez kampüs Güzelsanatlar Fakültesi", "39.86645603831937, 32.74941298353142", "dummySounzoneId", "dummyToken");
			VenueCreationDTO venueCreationDTO3 = new VenueCreationDTO("Keffçe", "Üniversiteler, Real-Praktiker Bilkent Station Avm 3/77, 06800 Çankaya/Ankara", "39.8839923932251, 32.75919199712163", "dummySounzoneId", "dummyToken");
			VenueCreationDTO venueCreationDTO4 = new VenueCreationDTO("Federal Coffee Bilkent", "Üniversiteler, Ankuva AVM No:12 D:30, 06800 Çankaya/Ankara", "39.8834821708473, 32.75638623924946", "dummySounzoneId", "dummyToken");
			VenueCreationDTO venueCreationDTO5 = new VenueCreationDTO("Starbucks", "Üniversiteler, K:Giriş Bilkent Center, 1597. Cd. No:3, 06800 Çankaya/Ankara", "39.88405539773744, 32.76122907128398", "dummySounzoneId", "dummyToken");

			VenueDTO resultVenue1 = venueService.addVenue(venueCreationDTO1);
			VenueDTO resultVenue2 = venueService.addVenue(venueCreationDTO2);
			VenueDTO resultVenue3 = venueService.addVenue(venueCreationDTO3);
			VenueDTO resultVenue4 = venueService.addVenue(venueCreationDTO4);
			VenueDTO resultVenue5 = venueService.addVenue(venueCreationDTO5);


			// ------------------------ Playlists ------------------------
			PlaylistCreationDTO playlistCreationDTO1 = new PlaylistCreationDTO(resultVenue1.getId(), "dummyID1", "dummyID1");
			PlaylistCreationDTO playlistCreationDTO2 = new PlaylistCreationDTO(resultVenue2.getId(), "dummyID2", "dummyID2");
			PlaylistCreationDTO playlistCreationDTO3 = new PlaylistCreationDTO(resultVenue3.getId(), "dummyID3", "dummyID3");
			PlaylistCreationDTO playlistCreationDTO4 = new PlaylistCreationDTO(resultVenue4.getId(), "dummyID4", "dummyID4");
			PlaylistCreationDTO playlistCreationDTO5 = new PlaylistCreationDTO(resultVenue5.getId(), "dummyID5", "dummyID5");

			PlaylistDTO resultPlaylist1 = playlistService.createPlaylist(playlistCreationDTO1);
			PlaylistDTO resultPlaylist2 = playlistService.createPlaylist(playlistCreationDTO2);
			PlaylistDTO resultPlaylist3 = playlistService.createPlaylist(playlistCreationDTO3);
			PlaylistDTO resultPlaylist4 = playlistService.createPlaylist(playlistCreationDTO4);
			PlaylistDTO resultPlaylist5 = playlistService.createPlaylist(playlistCreationDTO5);

			// ------------------------ Friends ------------------------
			friendshipService.sendFriendRequest(customerResult1.getUsername(), customerResult2.getUsername());
			friendshipService.sendFriendRequest(customerResult1.getUsername(), customerResult3.getUsername());
			friendshipService.sendFriendRequest(customerResult1.getUsername(), customerResult4.getUsername());
			friendshipService.sendFriendRequest(customerResult1.getUsername(), customerResult5.getUsername());

			friendshipService.sendFriendRequest(customerResult2.getUsername(), customerResult3.getUsername());
			friendshipService.sendFriendRequest(customerResult2.getUsername(), customerResult4.getUsername());
			friendshipService.sendFriendRequest(customerResult2.getUsername(), customerResult5.getUsername());

			friendshipService.sendFriendRequest(customerResult3.getUsername(), customerResult5.getUsername());
			friendshipService.sendFriendRequest(customerResult3.getUsername(), customerResult4.getUsername());

			friendshipService.sendFriendRequest(customerResult4.getUsername(), customerResult2.getUsername());
			friendshipService.sendFriendRequest(customerResult4.getUsername(), customerResult5.getUsername());


			friendshipService.acceptFriendRequest(customerResult1.getUsername(), customerResult2.getUsername());
			friendshipService.acceptFriendRequest(customerResult1.getUsername(), customerResult3.getUsername());
			friendshipService.acceptFriendRequest(customerResult1.getUsername(), customerResult5.getUsername());

			friendshipService.acceptFriendRequest(customerResult2.getUsername(), customerResult3.getUsername());
			friendshipService.acceptFriendRequest(customerResult2.getUsername(), customerResult5.getUsername());

			friendshipService.acceptFriendRequest(customerResult3.getUsername(), customerResult5.getUsername());
			friendshipService.acceptFriendRequest(customerResult3.getUsername(), customerResult4.getUsername());

			friendshipService.acceptFriendRequest(customerResult4.getUsername(), customerResult5.getUsername());


			// ------------------------ Coins ------------------------
			IncomingTransactionDTO incomingTransactionDTO1 = new IncomingTransactionDTO(TransactionTypes.ADVERTISEMENT, 1000.0, "date", "name", "surname", "card number", "expiration month", "year", "ccv");

			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult1.getUsername());
			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult2.getUsername());
			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult3.getUsername());
			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult4.getUsername());
			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult5.getUsername());
			transactionService.executeNewTransaction(incomingTransactionDTO1, customerResult6.getUsername());


			// ------------------------ Song Requests ------------------------
			SongRequestDTO songRequestDTO1 = new SongRequestDTO(24, customerResult1.getUsername(), 2, new Date(), 0,1.0);
			SongRequestDTO songRequestDTO2 = new SongRequestDTO(25, customerResult2.getUsername(), 2, new Date(), 0, 1.0);
			SongRequestDTO songRequestDTO3 = new SongRequestDTO(32, customerResult3.getUsername(), 2, new Date(), 0, 1.0);
			SongRequestDTO songRequestDTO4 = new SongRequestDTO(24, customerResult4.getUsername(), 2, new Date(), 0, 1.0);


			SongRequestDTO songRequestDTO5 = new SongRequestDTO(92, customerResult1.getUsername(), 2, new Date(), 0, 1.0);
			SongRequestDTO songRequestDTO6 = new SongRequestDTO(100, customerResult1.getUsername(), 2, new Date(), 0, 1.0);
			SongRequestDTO songRequestDTO7 = new SongRequestDTO(200, customerResult3.getUsername(), 2, new Date(), 0, 2.0);
			SongRequestDTO songRequestDTO8 = new SongRequestDTO(150, customerResult3.getUsername(), 2, new Date(), 0, 2.0);

			SongNodeDTO resultRequest2 = songService.addSongRequest(songRequestDTO2);
			SongNodeDTO resultRequest1 = songService.addSongRequest(songRequestDTO1);
			SongNodeDTO resultRequest3 = songService.addSongRequest(songRequestDTO3);
			SongNodeDTO resultRequest4 = songService.addSongRequest(songRequestDTO4);
			SongNodeDTO resultRequest5 = songService.addSongRequest(songRequestDTO5);
			SongNodeDTO resultRequest6 = songService.addSongRequest(songRequestDTO6);
			SongNodeDTO resultRequest7 = songService.addSongRequest(songRequestDTO7);
			SongNodeDTO resultRequest8 = songService.addSongRequest(songRequestDTO8);

			// ------------------------ Comments ------------------------
			CommentDTO commentDTO1 = new CommentDTO("This is a comment", "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getSurname(), resultVenue1.getId());
			CommentDTO commentDTO2 = new CommentDTO("This is a comment", "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue1.getId());
			CommentDTO commentDTO3 = new CommentDTO("This is a comment", "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue1.getId());

			CommentDTO commentDTO4 = new CommentDTO("This is a comment", "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue2.getId());
			CommentDTO commentDTO5 = new CommentDTO("This is a comment", "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue2.getId());
			CommentDTO commentDTO6 = new CommentDTO("This is a comment", "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue2.getId());

			CommentDTO commentDTO7 = new CommentDTO("This is a comment", "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue3.getId());
			CommentDTO commentDTO8 = new CommentDTO("This is a comment", "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue3.getId());
			CommentDTO commentDTO9 = new CommentDTO("This is a comment", "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue3.getId());

			CommentDTO commentDTO10 = new CommentDTO("This is a comment", "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue4.getId());
			CommentDTO commentDTO11 = new CommentDTO("This is a comment", "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue4.getId());
			CommentDTO commentDTO12 = new CommentDTO("This is a comment", "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue4.getId());

			commentService.addComment(commentDTO1);
			commentService.addComment(commentDTO2);
			commentService.addComment(commentDTO3);
			commentService.addComment(commentDTO4);
			commentService.addComment(commentDTO5);
			commentService.addComment(commentDTO6);
			commentService.addComment(commentDTO7);
			commentService.addComment(commentDTO8);
			commentService.addComment(commentDTO9);
			commentService.addComment(commentDTO10);
			commentService.addComment(commentDTO11);
			commentService.addComment(commentDTO12);

			// ------------------------ Ratings ------------------------
			RatingDTO ratingDTO1 = new RatingDTO(0, 5.0, "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue1.getId());
			RatingDTO ratingDTO2 = new RatingDTO(0, 3.0, "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue1.getId());
			RatingDTO ratingDTO3 = new RatingDTO(0, 4.0, "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue1.getId());

			RatingDTO ratingDTO4 = new RatingDTO(0, 5.0, "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue2.getId());
			RatingDTO ratingDTO5 = new RatingDTO(0, 2.0, "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue2.getId());
			RatingDTO ratingDTO6 = new RatingDTO(0, 4.0, "date", customerResult3.getUsername(),  customerResult3.getUsername(), customerResult3.getUsername(),resultVenue2.getId());

			RatingDTO ratingDTO7 = new RatingDTO(0, 5.0, "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), resultVenue3.getId());
			RatingDTO ratingDTO8 = new RatingDTO(0, 3.0, "date", customerResult2.getUsername(), customerResult2.getUsername(), customerResult2.getUsername(), resultVenue3.getId());
			RatingDTO ratingDTO9 = new RatingDTO(0, 4.0, "date", customerResult3.getUsername(), customerResult3.getUsername(), customerResult3.getUsername(), resultVenue3.getId());

			RatingDTO ratingDTO10 = new RatingDTO(0, 5.0, "date", customerResult1.getUsername(), customerResult1.getUsername(), customerResult1.getUsername(), 2);

			ratingService.addRating(ratingDTO1);
			ratingService.addRating(ratingDTO2);
			ratingService.addRating(ratingDTO3);
			ratingService.addRating(ratingDTO4);
			ratingService.addRating(ratingDTO5);
			ratingService.addRating(ratingDTO6);
			ratingService.addRating(ratingDTO7);
			ratingService.addRating(ratingDTO8);
			ratingService.addRating(ratingDTO9);
			ratingService.addRating(ratingDTO10);

			// ------------------------ Images ------------------------
			userService.uploadCustomerPhoto("905332346981", new ImageDTO("https://avatars.githubusercontent.com/u/62554574?v=4", 99L));
			userService.uploadCustomerPhoto("905309510454", new ImageDTO("https://avatars.githubusercontent.com/u/63585832?v=4", 99L));
			userService.uploadCustomerPhoto("905387866001", new ImageDTO("https://avatars.githubusercontent.com/u/55083192?v=4", 99L));

			venueService.uploadVenuePhoto(new ImageDTO("https://p2.nicelocal.biz.tr/preview/e1CI8cc-fuFbIARraC17Fw/1120x700x85/1/1/6/original_615812d87c0ab17c716b6785_615816fbade3d.jpg", 99L), 2);
			venueService.uploadVenuePhoto(new ImageDTO("https://yiyegeze.com/wp-content/uploads/2019/12/hersey-cok-guzelsende.jpg", 99L), 3);
			venueService.uploadVenuePhoto(new ImageDTO("https://fastly.4sqi.net/img/general/600x600/9401480_G1lYPMrGXFTmoSHrLEZld4DSQXnE5LIy6JfWSWk0JPE.jpg", 99L), 4);
			venueService.uploadVenuePhoto(new ImageDTO("https://w3.bilkent.edu.tr/www/wp-content/uploads/sites/5/2015/06/Station_fotograf-10-e1460463982134.jpg", 99L), 5);
			venueService.uploadVenuePhoto(new ImageDTO("https://fastly.4sqi.net/img/general/width960/64042939_1Ygst6BoNBAMMyQONw9E0zRZ06S28GpMPA7JYH-XIvQ.jpg", 99L), 6);
			venueService.uploadVenuePhoto(new ImageDTO("https://10619-2.s.cdn12.com/rests/original/804_54689832.jpg", 99L), 7);
			// ------------------------ Checkins ------------------------
			userService.checkIn("905332346981", 3);
			userService.checkIn("2", 4);
			userService.checkIn("905076011168", 5);

			// ------------------------ Venue Admins ------------------------
			VenueAdminCreationDTO venueAdminCreationDTO1 = new VenueAdminCreationDTO( "mehmet.yilmaz@bilkent.edu.tr", "Mehmet", "Yılmaz", "12345678", "mehmet.yilmaz@bilkent.edu.tr", "+905086023982", "Bilkent Merkez Spor Salonu");
			VenueAdminDTO venueAdminResult = userService.addVenueAdmin(venueAdminCreationDTO1);



			// song requests 
			playlistService.createDummySongRequests(800);
			playlistService.createBiasedDummySongRequests(200);
			venueService.createDummyVisits(10000, false);
			venueService.createDummyVisits(1000, true);

		};
	}

}

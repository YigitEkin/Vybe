package com.vybe.backend;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.CustomersAlreadyFriendsException;
import com.vybe.backend.exception.FriendshipNotFoundException;
import com.vybe.backend.exception.UsernameTakenException;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.Admin;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Friendship;
import com.vybe.backend.model.entity.VenueAdmin;
import com.vybe.backend.repository.*;
import com.vybe.backend.service.FriendshipService;
import com.vybe.backend.service.UserService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private FriendshipService friendshipService;

    @Mock private CustomerRepository customerRepository;
    @Mock private UserRepository userRepository;
    @Mock private AdminRepository adminRepository;
    @Mock private VenueAdminRepository venueAdminRepository;
    @Mock private FriendshipRepository friendshipRepository;


    // ********** CUSTOMER TESTS **********
    @Test
    void should_save_one_customer() {
        // Arrange
        final CustomerCreationDTO customerCreationDTO = new CustomerCreationDTO( "testpass1", "testname1", "testphone1", "testdate1", "testdate1", "000000");
        when(customerRepository.save(any(Customer.class))).thenReturn(customerCreationDTO.toCustomer());

        // Act
        final CustomerDTO actual = userService.addCustomer(customerCreationDTO);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(customerCreationDTO);
        verify(customerRepository, times(1)).save(any(Customer.class));
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void should_find_and_return_one_customer(){
        // Arrange
        final Customer customer = new Customer();
        customer.setUsername("testname1");
        customer.setPhoneNumber("testphone1");
        customer.setDateOfBirth("testdate1");
        customer.setDateOfCreation("testdate2");
        when(customerRepository.findByUsername("testname1")).thenReturn(customer);
        when(customerRepository.existsByUsername("testname1")).thenReturn(true);

        // Act
        final CustomerDTO actual = userService.getCustomer("testname1");

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(customer);
        verify(customerRepository, times(1)).findByUsername("testname1");
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void should_not_find_a_non_existent_customer(){
        // Arrange
        when(customerRepository.existsByUsername("testname1")).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> userService.getCustomer("testname1"));
        verify(customerRepository, times(1)).existsByUsername("testname1");
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void should_not_save_customer_with_taken_username(){
        // Arrange
        when(userRepository.existsByUsername("testname1")).thenReturn(true);
        CustomerCreationDTO customerCreationDTO = new CustomerCreationDTO( "testpass1", "testname1", "testphone1", "testdate1", "testdate1", "000000");

        // Act & Assert
        Assertions.assertThrows(UsernameTakenException.class, () -> userService.addCustomer(customerCreationDTO));
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void should_delete_one_customer(){
        // Arrange
        when(customerRepository.existsByUsername("testname1")).thenReturn(true);
        when(customerRepository.deleteByUsername("testname1")).thenReturn(1);

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> userService.deleteCustomer("testname1"));
        verify(customerRepository, times(1)).deleteByUsername("testname1");
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void should_not_delete_customer_with_non_existent_username(){
        // Arrange
        when(customerRepository.existsByUsername("testname1")).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> userService.deleteCustomer("testname1"));
        verify(customerRepository, times(0)).deleteByUsername("testname1");
        verifyNoMoreInteractions(customerRepository);
    }


    // ********** ADMIN TESTS **********
    @Test
    void should_save_one_admin() {
        // Arrange
        final AdminCreationDTO adminCreationDTO = new AdminCreationDTO( "testname2", "testpass2");
        when(adminRepository.save(any(Admin.class))).thenReturn(adminCreationDTO.toAdmin());

        // Act
        final AdminDTO actual = userService.addAdmin(adminCreationDTO);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(adminCreationDTO);
        verify(adminRepository, times(1)).save(any(Admin.class));
        verifyNoMoreInteractions(adminRepository);
    }


    // ********** VENUE ADMIN TESTS **********
    // TODO: get the venue of the venue admin working
    /* @Test
    void should_save_one_venueAdmin() {
        // Arrange
        final VenueAdminCreationDTO venueAdminCreationDTO = new VenueAdminCreationDTO( "testname3", "testpass3", "test_mail", "testphone", "venue", "000000");
        when(venueAdminRepository.save(any(VenueAdmin.class))).thenReturn(venueAdminCreationDTO.toVenueAdmin());

        // Act
        final VenueAdminDTO actual = userService.addVenueAdmin(venueAdminCreationDTO);

        // Assert
        assertThat(actual).usingRecursiveComparison().isEqualTo(venueAdminCreationDTO);
        verify(venueAdminRepository, times(1)).save(any(VenueAdmin.class));
        verifyNoMoreInteractions(venueAdminRepository);
    }
    */

    // ********** FRIENDSHIP TESTS **********
    @Test
    public void testGetFriends() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setUsername("user1");
        Customer customer2 = new Customer();
        customer2.setUsername("user2");
        Customer customer3 = new Customer();
        customer3.setUsername("user3");
        Customer customer4 = new Customer();
        customer4.setUsername("user4");

        // create some friendships
        Friendship friendship1 = new Friendship();
        friendship1.setSender(customer1);
        friendship1.setReceiver(customer2);
        friendship1.setAccepted(true);

        Friendship friendship2 = new Friendship();
        friendship2.setSender(customer3);
        friendship2.setReceiver(customer1);
        friendship2.setAccepted(true);

        Friendship friendship3 = new Friendship();
        friendship3.setSender(customer1);
        friendship3.setReceiver(customer4);
        friendship3.setAccepted(true);

        List<Friendship> friendshipList = new ArrayList<>();
        friendshipList.add(friendship1);
        friendshipList.add(friendship2);
        friendshipList.add(friendship3);

        when(customerRepository.existsById("user1")).thenReturn(true);
        when(customerRepository.findById("user1")).thenReturn(Optional.of(customer1));
        when(friendshipRepository.findBySenderAndAcceptedTrueOrReceiverAndAcceptedTrue(customer1)).thenReturn(friendshipList);

        // Act
        List<CustomerDTO> friendsList = friendshipService.getFriends("user1");

        // Assert
        assertThat(friendsList).hasSize(3);
        assertThat(friendsList).extracting(CustomerDTO::getUsername).containsOnly("user2", "user3", "user4");
    }

    @Test
    public void testGetFriendsWithNonExistingCustomer() {
        // Arrange
        String username = "user1";
        when(customerRepository.existsById(username)).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            friendshipService.getFriends(username);
        });
    }

    @Test
    public void testGetOutgoingFriendRequestsWithExistingCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("user1");
        when(customerRepository.existsById(customer.getUsername())).thenReturn(true);

        Customer friend1 = new Customer();
        friend1.setUsername("user2");
        Customer friend2 = new Customer();
        friend2.setUsername("user3");
        List<Friendship> outgoingRequests = new ArrayList<>();
        outgoingRequests.add(new Friendship(0L, customer, friend1, false));
        outgoingRequests.add(new Friendship(0L, customer, friend2, false));
        when(friendshipRepository.findBySenderAndAcceptedFalse(customer)).thenReturn(outgoingRequests);
        when(customerRepository.findById(customer.getUsername())).thenReturn(Optional.of(customer));


        // Act
        List<CustomerDTO> result = friendshipService.getOutgoingFriendRequests(customer.getUsername());

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getUsername().equals(friend1.getUsername())));
        assertTrue(result.stream().anyMatch(dto -> dto.getUsername().equals(friend2.getUsername())));
    }

    @Test
    void testGetIncomingFriendRequests() {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("user1");
        when(customerRepository.existsById("user1")).thenReturn(true);
        when(customerRepository.findById("user1")).thenReturn(Optional.of(customer));

        Friendship friendship = new Friendship();
        friendship.setSender(new Customer());
        friendship.getSender().setUsername("user2");
        friendship.setReceiver(customer);
        friendship.setAccepted(false);
        List<Friendship> friendshipList = new ArrayList<>();
        friendshipList.add(friendship);
        when(friendshipRepository.findByReceiverAndAcceptedFalse(customer)).thenReturn(friendshipList);

        // Act
        List<CustomerDTO> result = friendshipService.getIncomingFriendRequests("user1");

        // Assert
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0).getUsername());
    }

    @Test
    public void testSendFriendRequest() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender");
        Customer receiver = new Customer();
        receiver.setUsername("receiver");
        Friendship friendship = new Friendship(0L, sender, receiver, false);
        FriendshipDTO expectedFriendshipDTO = new FriendshipDTO(friendship);
        when(customerRepository.existsById("sender")).thenReturn(true);
        when(customerRepository.findById("sender")).thenReturn(Optional.of(sender));
        when(customerRepository.existsById("receiver")).thenReturn(true);
        when(customerRepository.findById("receiver")).thenReturn(Optional.of(receiver));
        when(friendshipRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(false);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        // Act
        FriendshipDTO actualFriendshipDTO = friendshipService.sendFriendRequest("sender", "receiver");

        // Assert
        assertThat(actualFriendshipDTO).usingRecursiveComparison().isEqualTo(expectedFriendshipDTO);
    }

    @Test
    public void testSendFriendRequestWithNonExistingSender() {
        // Arrange
        when(customerRepository.existsById("sender")).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            friendshipService.sendFriendRequest("sender", "receiver");
        });
    }

    @Test
    public void testSendFriendRequestWithNonExistingReceiver() {
        // Arrange
        when(customerRepository.existsById("sender")).thenReturn(true);
        when(customerRepository.existsById("receiver")).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            friendshipService.sendFriendRequest("sender", "receiver");
        });
    }

    @Test
    public void testSendFriendRequestWithAlreadyFriends() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender");
        Customer receiver = new Customer();
        receiver.setUsername("receiver");
        when(customerRepository.existsById("sender")).thenReturn(true);
        when(customerRepository.findById("sender")).thenReturn(Optional.of(sender));
        when(customerRepository.existsById("receiver")).thenReturn(true);
        when(customerRepository.findById("receiver")).thenReturn(Optional.of(receiver));
        when(friendshipRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(CustomersAlreadyFriendsException.class, () -> {
            friendshipService.sendFriendRequest("sender", "receiver");
        });
    }

    @Test
    void testAcceptFriendRequest() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender1");
        Customer receiver = new Customer();
        receiver.setUsername("receiver1");
        Friendship friendship = new Friendship(1L, sender, receiver, false);
        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(true);
        when(friendshipRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(true);
        when(friendshipRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(friendship);
        when(customerRepository.findById("sender1")).thenReturn(Optional.of(sender));
        when(customerRepository.findById("receiver1")).thenReturn(Optional.of(receiver));
        when(friendshipRepository.save(friendship)).thenReturn(friendship);

        // Act
        FriendshipDTO result = friendshipService.acceptFriendRequest("sender1", "receiver1");

        // Assert
        assertEquals(friendship.isAccepted(), result.isAccepted());
        verify(friendshipRepository).save(friendship);
    }

    @Test
    void testAcceptFriendRequestWithNonExistingSender() {
        // Arrange
        when(customerRepository.existsById("sender1")).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> friendshipService.acceptFriendRequest("sender1", "receiver1"));
        verify(customerRepository).existsById("sender1");
        verifyNoMoreInteractions(customerRepository, friendshipRepository);
    }

    @Test
    void testAcceptFriendRequestWithNonExistingReceiver() {
        // Arrange
        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> friendshipService.acceptFriendRequest("sender1", "receiver1"));
        verify(customerRepository).existsById("receiver1");
        verifyNoMoreInteractions(customerRepository, friendshipRepository);
    }

    @Test
    void testAcceptFriendRequestWithNonExistingFriendship() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender1");

        Customer receiver = new Customer();
        receiver.setUsername("receiver1");

        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(true);
        when(customerRepository.findById("sender1")).thenReturn(Optional.of(sender));
        when(customerRepository.findById("receiver1")).thenReturn(Optional.of(receiver));
        when(friendshipRepository.existsBySenderAndReceiver(any(Customer.class), any(Customer.class))).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(FriendshipNotFoundException.class, () -> friendshipService.acceptFriendRequest("sender1", "receiver1"));
        verify(friendshipRepository).existsBySenderAndReceiver(any(Customer.class), any(Customer.class));
        verifyNoMoreInteractions(customerRepository, friendshipRepository);
    }


    @Test
    void testDeclineFriendRequest() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender1");
        Customer receiver = new Customer();
        receiver.setUsername("receiver1");

        Friendship friendship = new Friendship(1L, sender, receiver, false);
        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(true);
        when(customerRepository.findById("sender1")).thenReturn(Optional.of(sender));
        when(customerRepository.findById("receiver1")).thenReturn(Optional.of(receiver));
        when(friendshipRepository.existsBySenderAndReceiver(sender, receiver)).thenReturn(true);
        when(friendshipRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(friendship);

        // Act
        assertDoesNotThrow(() -> friendshipService.declineFriendRequest("sender1", "receiver1"));

        // Assert
        verify(friendshipRepository).delete(friendship);
    }

    @Test
    void testDeclineFriendRequest_CustomerNotFound() {
        // Arrange
        when(customerRepository.existsById("sender1")).thenReturn(false);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> friendshipService.declineFriendRequest("sender1", "receiver1"));
    }

    @Test
    void testDeclineFriendRequest_FriendshipNotFound() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender1");
        Customer receiver = new Customer();
        receiver.setUsername("receiver1");

        when(customerRepository.findById("sender1")).thenReturn(Optional.of(sender));
        when(customerRepository.findById("receiver1")).thenReturn(Optional.of(receiver));
        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(true);
        when(friendshipRepository.existsBySenderAndReceiver(any(Customer.class), any(Customer.class))).thenReturn(false);

        // Act & Assert
        assertThrows(FriendshipNotFoundException.class, () -> friendshipService.declineFriendRequest("sender1", "receiver1"));
    }

    @Test
    void testRemoveFriend() {
        // Arrange
        Customer sender = new Customer();
        sender.setUsername("sender1");
        Customer receiver = new Customer();
        receiver.setUsername("receiver1");
        Friendship friendship = new Friendship(1L, sender, receiver, true);
        when(customerRepository.existsById("sender1")).thenReturn(true);
        when(customerRepository.existsById("receiver1")).thenReturn(true);
        when(friendshipRepository.existsBySenderAndReceiverOrReceiverAndSender(sender, receiver)).thenReturn(true);
        when(customerRepository.findById("sender1")).thenReturn(Optional.of(sender));
        when(customerRepository.findById("receiver1")).thenReturn(Optional.of(receiver));

        // Act
        friendshipService.removeFriend("sender1", "receiver1");

        // Assert
        verify(friendshipRepository).deleteBySenderAndReceiverOrReceiverAndSender(sender, receiver);
    }
}

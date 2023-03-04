package com.vybe.backend;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.UsernameTakenException;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.Admin;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.VenueAdmin;
import com.vybe.backend.repository.AdminRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.VenueAdminRepository;
import com.vybe.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock private CustomerRepository customerRepository;
    @Mock private UserRepository userRepository;
    @Mock private AdminRepository adminRepository;
    @Mock private VenueAdminRepository venueAdminRepository;


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
        when(customerRepository.deleteByUsername("testname1")).thenReturn(true);

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

    
}

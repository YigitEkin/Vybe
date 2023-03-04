package com.vybe.backend;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.Admin;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.VenueAdmin;
import com.vybe.backend.repository.AdminRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.UserRepository;
import com.vybe.backend.repository.VenueAdminRepository;
import com.vybe.backend.service.UserService;
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

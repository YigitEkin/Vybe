package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private String username;
    private String name;
    private String surname;
    private String phoneNumber;
    private String dateOfBirth;
    private String dateOfCreation;
    private VenueDTO checkedInVenue;

    public CustomerDTO(Customer customer) {
        this.username = customer.getUsername();
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.phoneNumber = customer.getPhoneNumber();
        this.dateOfBirth = customer.getDateOfBirth();
        this.dateOfCreation = customer.getDateOfCreation();
        if (customer.getCheckedInVenue() != null)
            this.checkedInVenue = new VenueDTO(customer.getCheckedInVenue());
    }
}

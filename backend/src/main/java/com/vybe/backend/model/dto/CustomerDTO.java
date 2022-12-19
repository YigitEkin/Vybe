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
    private String phoneNumber;
    private String dateOfBirth;
    private String dateOfCreation;

    public CustomerDTO(Customer customer) {
        this.username = customer.getUsername();
        this.phoneNumber = customer.getPhoneNumber();
        this.dateOfBirth = customer.getDateOfBirth();
        this.dateOfCreation = customer.getDateOfCreation();
    }
}

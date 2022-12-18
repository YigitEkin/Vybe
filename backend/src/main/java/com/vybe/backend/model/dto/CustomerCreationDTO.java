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
public class CustomerCreationDTO {
    private String password;
    private String username;
    private String phoneNumber;
    private String dateOfBirth;
    private String dateOfCreation;

    private String code;
    public Customer toCustomer() {
        return new Customer(username, password, phoneNumber, null, null, null, null, dateOfBirth, dateOfCreation);
    }

}

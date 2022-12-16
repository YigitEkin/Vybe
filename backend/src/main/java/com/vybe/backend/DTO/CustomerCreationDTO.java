package com.vybe.backend.DTO;


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

    public Customer toCustomer() {
        return new Customer(username, password, phoneNumber, null, null, null, null, dateOfBirth, dateOfCreation);
    }

}

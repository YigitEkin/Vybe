package com.vybe.backend.DTO;

import com.vybe.backend.model.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminCreationDTO {
    private String username;
    private String password;

    public Admin toAdmin() {
        return new Admin(username, password);
    }
}

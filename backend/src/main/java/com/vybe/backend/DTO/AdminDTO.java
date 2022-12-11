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
public class AdminDTO {
    private String username;

    public AdminDTO(Admin admin) {
        this.username = admin.getUsername();
    }
}

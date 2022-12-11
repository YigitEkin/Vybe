package com.vybe.backend.DTO;


import com.vybe.backend.model.entity.VenueAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenueAdminCreationDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String venueName;

    public VenueAdminCreationDTO(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public VenueAdmin toVenueAdmin() {
        return new VenueAdmin(username, password, email, phoneNumber, null, null, null, true);
    }
}

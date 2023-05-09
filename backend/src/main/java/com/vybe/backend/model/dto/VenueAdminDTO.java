package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.VenueAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenueAdminDTO {
    private String username;
    private String name;
    private String surname;
    private String phoneNumber;
    private String venueName;

    public VenueAdminDTO(VenueAdmin venueAdmin) {
        this.username = venueAdmin.getUsername();
        this.phoneNumber = venueAdmin.getPhoneNumber();
        this.name = venueAdmin.getName();
        this.surname = venueAdmin.getSurname();
        //this.venueName = venueAdmin.getVenue().getName();
    }
}

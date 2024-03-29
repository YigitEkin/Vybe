package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VenueCreationDTO {
    private String name;
    private String description;
    private String location;
    private String soundzoneId;
    private String token;

    public Venue toVenue() {
        return new Venue(0, name, description, location, null, null, null, null, null, null, null, token, soundzoneId, null, 0.0);
    }
}

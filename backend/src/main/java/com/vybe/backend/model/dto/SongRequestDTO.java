package com.vybe.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongRequestDTO {
    private Integer songId;
    private String requestedByUsername;
    private Integer requestedInVenueId;
    private Integer playlistId;
    private String requestDate;
}

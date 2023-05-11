package com.vybe.backend.model.dto;

import com.vybe.backend.model.entity.SongRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongRequestDTO {
    private Integer songId; // required for creation
    private String requestedByUsername; // required for creation
    private Integer requestedInVenueId; // required for creation
    private Date requestDate; // not required for creation
    private Integer playlistId; // not required for creation
    private Double coinCost;
    public SongRequestDTO(SongRequest songRequest){
        this.songId = songRequest.getSong().getId();
        this.requestedByUsername = songRequest.getRequestedBy().getUsername();
        this.requestedInVenueId = songRequest.getRequestedInVenue().getId();
        this.playlistId = songRequest.getRequestedInVenue().getPlaylist().getId();
        this.requestDate = songRequest.getRequestDate();
        this.coinCost = songRequest.getCoinCost();
    }
}

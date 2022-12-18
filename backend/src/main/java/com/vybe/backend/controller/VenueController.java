package com.vybe.backend.controller;

import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.service.PlaylistService;
import com.vybe.backend.service.VenueService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/venue")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VenueController {

    @Resource
    private VenueService venueService;

    @Resource
    private PlaylistService playlistService;

    @GetMapping("/{venueId}/nextSong")
    public SongDTO playNextSong(@PathVariable Integer venueId) {
        return new SongDTO(venueService.startSong(venueId));
    }

    @PostMapping("/{venueId}/defaultPlaylist")
    public List<SongDTO> syncDefaultPlaylist(@PathVariable Integer venueId) {
        return playlistService.addAllSongsToDefaultPlaylist(venueId);
    }
}

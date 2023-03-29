package com.vybe.backend.controller;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.service.CommentService;
import com.vybe.backend.service.PlaylistService;
import com.vybe.backend.service.SongService;
import com.vybe.backend.service.VenueService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/venue")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class VenueController {

    @Resource
    private VenueService venueService;

    @Resource
    private PlaylistService playlistService;

    @Resource
    private CommentService commentService;

    @Resource
    private SongService songService;


    // ************* Venue Endpoints ************* //
    @GetMapping("/{venueId}")
    public VenueDTO getVenue(@PathVariable Integer venueId) {
        return venueService.getVenue(venueId);
    }

    // get all venues
    @GetMapping()
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }

    // create a venue
    @PostMapping()
    public VenueDTO createVenue(@RequestBody VenueCreationDTO venueCreationDTO) {
        return venueService.addVenue(venueCreationDTO);
    }

    // update a venue
    @PutMapping("/{venueId}")
    public VenueDTO updateVenue(@PathVariable Integer venueId, @RequestBody VenueCreationDTO venueCreationDTO) {
        return venueService.updateVenue(venueId, venueCreationDTO);
    }

    // delete a venue
    @DeleteMapping("/{venueId}")
    public void deleteVenue(@PathVariable Integer venueId) {
        venueService.deleteVenue(venueId);
    }

    // ************* Comment Endpoints ************* //
    @GetMapping("/{venueId}/comments")
    public List<CommentDTO> getComments(@PathVariable Integer venueId) {
        return commentService.getAllCommentsForVenue(venueId);
    }

    @GetMapping("/{venueId}/nextSong")
    public SongDTO playNextSong(@PathVariable Integer venueId) {

        return new SongDTO(venueService.startSong(venueId));
    }

    @PostMapping("/{venueId}/defaultPlaylist")
    public List<SongDTO> syncDefaultPlaylist(@PathVariable Integer venueId) {
        return playlistService.addAllSongsToDefaultPlaylist(venueId);
    }


}

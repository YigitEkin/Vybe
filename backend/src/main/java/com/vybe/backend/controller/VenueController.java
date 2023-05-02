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
@RequestMapping("/api/venues")
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


    // ************* Song Endpoints ************* //

    @GetMapping("/{venueId}/nextSong")
    public SongDTO playNextSong(@PathVariable Integer venueId) {

        return new SongDTO(venueService.startSong(venueId));
    }

    @PostMapping("/{venueId}/defaultPlaylist")
    public List<SongDTO> syncDefaultPlaylist(@PathVariable Integer venueId) {
        return playlistService.addAllSongsToDefaultPlaylist(venueId);
    }


    @GetMapping("/{venueId}/defaultPlaylist/songs")
    public List<SongDTO> getDefaultPlaylistSongs(@PathVariable Integer venueId) {
        return playlistService.getSongsOfDefaultPlaylist(venueId);
    }

    @GetMapping("/{venueId}/requestsQueue/songs")
    public List<SongNodeDTO> getRequestsQueueSongs(@PathVariable Integer venueId) {
        return songService.getAllSongNodesByVenueId(venueId);
    }

    @GetMapping("/{venueId}/nextSongs")
    public List<SongDTO> getNextSongs(@PathVariable Integer venueId) {
        return songService.getNextXSongs(venueId, 10);
    }

    // ************* Song Request Endpoints ************* //
    @GetMapping("/{venueId}/songRequests")
    public List<SongRequestDTO> getSongRequests(@PathVariable Integer venueId) {
        return songService.getSongRequestsByVenueId(venueId);
    }

}

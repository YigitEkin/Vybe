package com.vybe.backend.controller;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.model.entity.Image;
import com.vybe.backend.util.SchedulerUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import com.vybe.backend.model.entity.Visit;
import com.vybe.backend.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")

// TODO: restrict origins
public class VenueController {

    @Resource
    private VenueService venueService;

    @Resource
    private PlaylistService playlistService;

    @Resource
    private CommentService commentService;

    @Resource
    private RatingService ratingService;

    @Resource
    private SongService songService;

    @Resource
    private UserService userService;

    @Resource
    private VisitService visitService;

    @Resource
    private SchedulerUtil schedulerUtil;

    @Resource
    private AnalyticsService analyticsService;


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

    // ************* Rating Endpoints ************* //
    @GetMapping("/{venueId}/ratings")
    public List<RatingDTO> getRatings(@PathVariable Integer venueId) {
        return ratingService.getAllRatingsForVenue(venueId);
    }

    @GetMapping("/{venueId}/ratings/average")
    public Double getAverageRating(@PathVariable Integer venueId) {
        return ratingService.getAverageRatingForVenue(venueId);
    }

    // ************* Song Endpoints ************* //

    @GetMapping("/{venueId}/nextSong")
    public SongDTO playNextSong(@PathVariable Integer venueId) {
        return new SongDTO(venueService.startSong(venueId));
    }

    @GetMapping("/{venueId}/currentSong")
    public SongDTO getCurrentSong(@PathVariable Integer venueId) {
        return venueService.getCurrentSong(venueId);
    }

    @PostMapping("/{venueId}/defaultPlaylist")
    public List<SongDTO> syncDefaultPlaylist(@PathVariable Integer venueId) {
        return playlistService.addAllSongsToDefaultPlaylist(venueId);
    }
    @PostMapping("/{venueId}/initialize")
    public void initializeVenue(@PathVariable Integer venueId) {
        schedulerUtil.initializeVenue(venueId);
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

    // ************* Check in/out Endpoints ************* //
    @PostMapping("/{venueId}/checkIn/{username}")
    public String checkIn(@PathVariable Integer venueId, @PathVariable String username) {
        return userService.checkIn(username, venueId);
    }

    @PostMapping("/{venueId}/checkOut/{username}")
    public String checkOut(@PathVariable Integer venueId, @PathVariable String username) {
        return userService.checkOut(username, venueId);
    }

    // get checked in customers
    @GetMapping("/{venueId}/checkedIn")
    public List<CustomerDTO> getCheckedInCustomers(@PathVariable Integer venueId) {
        return venueService.getCheckedInCustomers(venueId);
    }

    // ************* Visit Endpoints ************* //
    @GetMapping("/{venueId}/visits")
    public List<Visit> getVisits(@PathVariable Integer venueId) {
        return visitService.getAllVisitsByVenueId(venueId);
    }

    @GetMapping("/{venueId}/visits/{username}")
    public List<Visit> getVisitsByCustomerUsername(@PathVariable Integer venueId, @PathVariable String username) {
        return visitService.getAllVisitsByCustomerUsername(username);
    }

    // get todays visits
    @GetMapping("/{venueId}/visits/today")
    public List<Visit> getTodaysVisits(@PathVariable Integer venueId) {
        return visitService.getVisitsByVenueIdAndToday(venueId);
    }

    // ************* Image Endpoints ************* //
    @GetMapping("/{venueId}/images")
    public List<ImageDTO> getImage(@PathVariable Integer venueId) {
        return venueService.getVenuePhotos(venueId);
    }

    @PostMapping("/{venueId}/images")
    public Boolean addImage(@PathVariable Integer venueId, @RequestBody ImageDTO imageDTO) {
        return venueService.uploadVenuePhoto(imageDTO, venueId);
    }

    @DeleteMapping("/{venueId}/images/{imageId}")
    public Boolean deleteImage(@PathVariable Integer venueId, @PathVariable Long imageId) {
        return venueService.deleteVenuePhoto(venueId, imageId);
    }

    // ************* Analytics Endpoints ************* //
    // with query params of per moth, per week, per day
    @GetMapping("/{venueId}/analytics/checkIns")
    public Integer[] getCheckIns(@PathVariable Integer venueId, @RequestParam(required = false) String inAYear, @RequestParam(required = false) String inAMonth,
                                     @RequestParam(required = false) String inADay, @RequestParam(required = false) String inADayPer4Hours) {
        return analyticsService.getCheckIns(venueId, inAYear, inAMonth, inADay, inADayPer4Hours);
    }

    @GetMapping("/{venueId}/analytics/topRequests")
    public List<SongDTO> getTopRequests(@PathVariable Integer venueId) {
        return analyticsService.getTop10RequestedSongs(venueId);
    }

    @GetMapping("/{venueId}/analytics/recentRequests")
    public List<SongDTO> getRecentlyPlayed(@PathVariable Integer venueId) {
        return analyticsService.getRecentlyRequestedSongs(venueId);
    }

    @GetMapping("/{venueId}/analytics/requestsPerArtist")
    public List<String> getSongsPerArtist(@PathVariable Integer venueId) {
        return analyticsService.getSongsRequestedPerArtist(venueId);
    }

    @GetMapping("/{venueId}/analytics/requests")
    public Integer[] getRequests(@PathVariable Integer venueId, @RequestParam(required = false) String inAYear, @RequestParam(required = false) String inAMonth,
    @RequestParam(required = false) String inADay, @RequestParam(required = false) String inADayPer4Hours) {
        return analyticsService.getSongRequestCounts(venueId, inAYear, inAMonth, inADay, inADayPer4Hours);
    }

    @GetMapping("/{venueId}/analytics/coinsSpent")
    public Double[] getCoinsSpent(@PathVariable Integer venueId, @RequestParam(required = false) String inAYear, @RequestParam(required = false) String inAMonth,
    @RequestParam(required = false) String inADay, @RequestParam(required = false) String inADayPer4Hours) {
        return analyticsService.getCoinsSpentOnSongRequests(venueId, inAYear, inAMonth, inADay, inADayPer4Hours);
    }








}

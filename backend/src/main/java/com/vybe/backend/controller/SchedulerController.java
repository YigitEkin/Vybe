package com.vybe.backend.controller;

import com.vybe.backend.service.VenueService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/scheduler")

@CrossOrigin(origins = "localhost:8080", allowedHeaders = "*")
public class SchedulerController {

    @Resource
    private VenueService venueService;



    @GetMapping("/{venueId}/nextSongScheduled")
    public String playNextSongScheduled(@PathVariable Integer venueId) {
        venueService.startSongScheduled(venueId);
        return "Success";
    }
}

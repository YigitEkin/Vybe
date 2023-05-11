package com.vybe.backend.controller;

import com.vybe.backend.model.dto.RatingDTO;
import com.vybe.backend.service.RatingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class RatingController {
    @Resource private RatingService ratingService;

    // get all ratings
    @GetMapping()
    public Iterable<RatingDTO> getAllRatings() {
        return ratingService.getAllRatings();
    }

    // get specific rating
    @GetMapping("/{id}")
    public RatingDTO getRating(@PathVariable Integer id) {
        return ratingService.getRating(id);
    }

    // create a rating
    @PostMapping()
    public RatingDTO createRating(@RequestBody RatingDTO ratingDTO) {
        return ratingService.addRating(ratingDTO);
    }

    // update a rating
    @PutMapping("/{id}")
    public RatingDTO updateRating(@PathVariable Integer id, @RequestBody RatingDTO ratingDTO) {
        return ratingService.updateRating(id, ratingDTO);
    }

    // delete a rating
    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
    }
}

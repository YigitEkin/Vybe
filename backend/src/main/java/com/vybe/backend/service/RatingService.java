package com.vybe.backend.service;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.RatingNotFoundException;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.dto.RatingDTO;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Rating;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.CommentRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.RatingRepository;
import com.vybe.backend.repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingService {

    @Resource private CommentRepository commentRepository;
    @Resource private CustomerRepository customerRepository;
    @Resource private VenueRepository venueRepository;
    @Resource private RatingRepository ratingRepository;

    // add a rating
    public RatingDTO addRating(RatingDTO ratingCreationDTO) {
        if(!venueRepository.existsById(ratingCreationDTO.getVenueId())) {
            throw new VenueNotFoundException("Venue with id: " + ratingCreationDTO.getVenueId() + " not found");
        }
        if(!customerRepository.existsById(ratingCreationDTO.getCustomerUsername())) {
            throw new CustomerNotFoundException("Customer with username: " + ratingCreationDTO.getCustomerUsername() + " not found");
        }
        Venue venue = venueRepository.findById(ratingCreationDTO.getVenueId()).get();
        Customer customer = customerRepository.findById(ratingCreationDTO.getCustomerUsername()).get();
        Rating rating = ratingCreationDTO.toRating();
        rating.setDate(new Date());
        rating.setVenue(venue);
        rating.setRatedBy(customer);
        RatingDTO ratingDTO = new RatingDTO(ratingRepository.save(rating));
        venue.setRating(ratingRepository.getAverageRatingForVenue(ratingDTO.getVenueId()));
        venueRepository.save(venue);
        return ratingDTO;
    }

    // get all ratings
    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    // get all ratings for a venue
    public List<RatingDTO> getAllRatingsForVenue(int venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        return ratingRepository.findAllByVenueId(venueId).stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    // get all ratings by a customer
    public List<RatingDTO> getAllRatingsByCustomer(String customerUsername) {
        if(!customerRepository.existsById(customerUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + customerUsername + " not found");
        }
        return ratingRepository.findAllByRatedBy_Username(customerUsername).stream().map(RatingDTO::new).collect(Collectors.toList());
    }

    // get Average rating for a venue
    public double getAverageRatingForVenue(int venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new RatingNotFoundException("Venue with id: " + venueId + " not found");
        }
        if(!ratingRepository.existsByVenueId(venueId)) {
            return 0.0;
        }
        return ratingRepository.getAverageRatingForVenue(venueId);
    }

    // get rating
    public RatingDTO getRating(int ratingId) {
        if(!ratingRepository.existsById(ratingId)) {
            throw new RatingNotFoundException("Rating with id: " + ratingId + " not found");
        }
        return new RatingDTO(ratingRepository.findById(ratingId).get());
    }

    // update rating
    public RatingDTO updateRating(int ratingId, RatingDTO ratingUpdateDTO) {
        if(!ratingRepository.existsById(ratingId)) {
            throw new RatingNotFoundException("Rating with id: " + ratingId + " not found");
        }
        if(!venueRepository.existsById(ratingUpdateDTO.getVenueId())) {
            throw new VenueNotFoundException("Venue with id: " + ratingUpdateDTO.getVenueId() + " not found");
        }
        Rating rating = ratingRepository.findById(ratingId).get();
        rating.setRating(ratingUpdateDTO.getRating());
        RatingDTO ratingDTO = new RatingDTO(ratingRepository.save(rating));
        double newAverageRating = ratingRepository.getAverageRatingForVenue(ratingDTO.getVenueId());
        Venue venue = venueRepository.findById(ratingDTO.getVenueId()).get();
        venue.setRating(newAverageRating);
        venueRepository.save(venue);
        return ratingDTO;
    }

    // delete rating
    public void deleteRating(int ratingId) {
        if(!ratingRepository.existsById(ratingId)) {
            throw new RatingNotFoundException("Rating with id: " + ratingId + " not found");
        }
        ratingRepository.deleteById(ratingId);
    }

    // delete all ratings for a venue
    public void deleteAllRatingsForVenue(int venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        ratingRepository.deleteAllByVenueId(venueId);
    }

    // delete all ratings by a customer
    public void deleteAllRatingsByCustomer(String customerUsername) {
        if(!customerRepository.existsById(customerUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + customerUsername + " not found");
        }
        ratingRepository.deleteAllByRatedBy_Username(customerUsername);
    }
}

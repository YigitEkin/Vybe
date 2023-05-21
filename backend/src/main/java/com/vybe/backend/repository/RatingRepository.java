package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findAllByVenueId(int venueId);

    List<Rating> findAllByRatedBy_Username(String customerUsername);

    Integer deleteAllByVenueId(int venueId);

    Integer deleteAllByRatedBy_Username(String customerUsername);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.venue.id = ?1")
    double getAverageRatingForVenue(int venueId);

    boolean existsByVenueId(int venueId);
}

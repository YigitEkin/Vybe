package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {

    boolean existsByName(String name);
    Venue findByName(String name);
}

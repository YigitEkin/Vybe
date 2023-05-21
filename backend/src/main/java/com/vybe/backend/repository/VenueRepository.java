package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Venue v WHERE v.name = ?1")
    boolean existsByName(String name);
    Venue findByName(String name);
}

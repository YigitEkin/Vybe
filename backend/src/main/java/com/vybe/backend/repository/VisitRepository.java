package com.vybe.backend.repository;


import com.vybe.backend.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {
    // find all by venueId
    List<Visit> findAllByVenueId(Integer venueId);

    List<Visit> findAllByCustomerUsername(String customerUsername);

    List<Visit> findAllByCustomerUsernameAndVenueId(String customerUsername, Integer venueId);

}

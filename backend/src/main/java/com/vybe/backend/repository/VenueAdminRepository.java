package com.vybe.backend.repository;

import com.vybe.backend.model.entity.VenueAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueAdminRepository extends JpaRepository<VenueAdmin, String> {

    boolean existsByUsername(String username);
    VenueAdmin findByUsername(String username);
    void deleteByUsername(String username);
    VenueAdmin findByVenueId(Integer venueId);
}

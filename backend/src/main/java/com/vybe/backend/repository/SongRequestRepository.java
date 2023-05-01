package com.vybe.backend.repository;

import com.vybe.backend.model.entity.SongRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRequestRepository extends JpaRepository<SongRequest, Integer> {
    List<SongRequest> findByRequestedInVenueId(Integer requestedInVenueId);

    List<SongRequest> findByRequestedByUsername(String requestedByUsername);

    List<SongRequest> findBySongId(Integer songId);
}

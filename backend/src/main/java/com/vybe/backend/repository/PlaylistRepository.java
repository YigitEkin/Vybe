package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    Optional<Playlist> findByVenueId(Integer venueId);
}

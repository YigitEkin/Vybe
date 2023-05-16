package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.SongRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRequestRepository extends JpaRepository<SongRequest, Integer> {
    List<SongRequest> findByRequestedInVenueId(Integer requestedInVenueId);

    List<SongRequest> findByRequestedByUsername(String requestedByUsername);

    @Query(value = "SELECT * FROM song_request WHERE song_id = ?1", nativeQuery = true)
    List<SongRequest> findBySongId(Integer songId);

    // query for finding the top 10 most requested songs by venueId
    @Query(value = "SELECT top10.song_id FROM (SELECT song_id, COUNT(song_id) AS count FROM song_request WHERE requested_in_venue_id = ?1 GROUP BY song_id ORDER BY count DESC LIMIT 10) AS top10 INNER JOIN song ON top10.song_id = song.id", nativeQuery = true)
    List<Integer> findTop10RequestsByVenueId(Integer venueId);

    // query for finding the top 20 most recent song requests by venueId
    @Query(value = "SELECT * FROM song_request WHERE requested_in_venue_id = ?1 ORDER BY request_date DESC LIMIT 20", nativeQuery = true)
    List<SongRequest> findTop20RecentRequestsByVenueId(Integer venueId);
}

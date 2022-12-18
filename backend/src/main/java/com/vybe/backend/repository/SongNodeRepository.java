package com.vybe.backend.repository;

import com.vybe.backend.model.entity.SongNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongNodeRepository extends JpaRepository<SongNode, Integer> {

    boolean existsBySong_IdAndPlaylistId(String song_id, Integer playlistId);
    SongNode findBySong_IdAndPlaylistId(String songId, Integer playlistId);
}

package com.vybe.backend.repository;

import com.vybe.backend.model.entity.SongRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRequestRepository extends JpaRepository<SongRequest, Integer> {


}

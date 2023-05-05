package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRepository  extends JpaRepository<Streak, Integer> {

    Optional<Streak> findByCustomerUsernameAndVenueId(String customer_username, Integer venue_id);

    List<Streak> findAllByCustomerUsername(String customer_username);
}

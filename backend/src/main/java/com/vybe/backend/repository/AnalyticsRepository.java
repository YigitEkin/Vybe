package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {


}

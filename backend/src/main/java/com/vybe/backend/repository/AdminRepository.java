package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {


    boolean existsByUsername(String username);
    Admin findByUsername(String username);
    void deleteByUsername(String username);
}

package com.vybe.backend.repository;

import com.vybe.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> getUserByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}



package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Customer findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}


package com.enigma.carrent.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.carrent.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByNikAndUserAccount_id(String customerId, String id);

    Optional<Customer> findByNik(String id);

}

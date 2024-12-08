package com.enigma.carrent.repository;

import com.enigma.carrent.entity.RentTransaction;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentTransactionRepository extends JpaRepository<RentTransaction, String> {
    Optional<RentTransaction> findById(String id);
}

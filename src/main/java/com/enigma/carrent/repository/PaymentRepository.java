package com.enigma.carrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enigma.carrent.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT p FROM Payment p WHERE p.transactionId.id = :transactionId")
    Payment findByTransactionId(@Param("transactionId") String transactionId);
}

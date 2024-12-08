package com.enigma.carrent.repository;

import com.enigma.carrent.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, String> {
    UserAccount findByUsernameAndPassword(String username, String password);
    Optional<UserAccount> findByUsername(String username);
}

package com.enigma.carrent.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enigma.carrent.entity.UserAccount;
import com.enigma.carrent.repository.UserRepository;
import com.enigma.carrent.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserAccount getByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserAccount getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserAccount createUser(UserAccount userAccount) {
        UserAccount newUser = new UserAccount();
        newUser.setUsername(userAccount.getUsername());
        newUser.setPassword(userAccount.getPassword());
        newUser.setRole(userAccount.getRole());
        userRepository.saveAndFlush(userAccount);
        return newUser;
    }

    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        return userAccount;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userRepository.findByUsername(username);
        System.out.println("loadUserByUsername" + user);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}

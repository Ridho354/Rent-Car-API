package com.enigma.carrent.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.enigma.carrent.entity.UserAccount;
import com.enigma.carrent.repository.CustomerRepository;
import com.enigma.carrent.service.PermissionEvaluationService;

@Service
@RequiredArgsConstructor
public class PermissionEvaluationServiceImpl implements PermissionEvaluationService {
    private final CustomerRepository customerRepository;

    @Override
    public boolean hasAccessToCustomer(String customerId, UserAccount userAccount) {
        return customerRepository.existsByNikAndUserAccount_id(customerId, userAccount.getId());
    }
}

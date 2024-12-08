package com.enigma.carrent.service;

import com.enigma.carrent.entity.UserAccount;

public interface PermissionEvaluationService {
    public boolean hasAccessToCustomer(String customerId, UserAccount userAccount);
}

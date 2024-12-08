package com.enigma.carrent.service;

import com.enigma.carrent.dto.request.AuthRequest;
import com.enigma.carrent.dto.request.RegisterCustomerRequest;
import com.enigma.carrent.dto.response.LoginResponse;
import com.enigma.carrent.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterCustomerRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    RegisterResponse registerSuperAdmin(AuthRequest request, String superAdminRequestHeader);
    LoginResponse login(AuthRequest request);
}

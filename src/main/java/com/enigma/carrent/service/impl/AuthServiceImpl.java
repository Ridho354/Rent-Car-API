package com.enigma.carrent.service.impl;

import com.enigma.carrent.dto.request.AuthRequest;
import com.enigma.carrent.dto.request.CustomerRequest;
import com.enigma.carrent.dto.request.CustomerRequestRegister;
import com.enigma.carrent.dto.request.RegisterCustomerRequest;
import com.enigma.carrent.dto.response.LoginResponse;
import com.enigma.carrent.dto.response.RegisterResponse;
import com.enigma.carrent.entity.UserAccount;
import com.enigma.carrent.constant.UserRole;
import com.enigma.carrent.service.AuthService;
import com.enigma.carrent.service.CustomerService;
import com.enigma.carrent.service.JwtService;
import com.enigma.carrent.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final CustomerService customerService;
    @Value("${rentcar_api.secret.super_admin_header_key}")
    private String superAdminHeaderKey;

    @Override
    public RegisterResponse register(RegisterCustomerRequest request) {
        validateUsernameNotExists(request.getUsername());
        System.out.println("#".repeat(50));
        System.out.println(request.getNik().toString());
        System.out.println("#".repeat(50));
        UserAccount userAccount = toUserAccount(request.getUsername(), request.getPassword(), UserRole.ROLE_CUSTOMER);
        UserAccount savedUserAccount = userService.createUser(userAccount);
        System.out.println("-".repeat(50));
        System.out.println(savedUserAccount.toString());
        System.out.println("-".repeat(50));
        customerService.createCustomer(new CustomerRequestRegister().builder()
                .nik(request.getNik())
                .name(request.getName())
                .driving_license(request.getDrivingLicense())
                .phone(request.getPhoneNumber())
                .address(request.getAddress())
                .userAccount(userAccount)
                .build());

        return toRegisterResponse(userAccount);
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        validateUsernameNotExists(request.getUsername());

        UserAccount userAccount = toUserAccount(request.getUsername(), request.getPassword(), UserRole.ROLE_ADMIN);
        userService.createUser(userAccount);

        return toRegisterResponse(userAccount);
    }

    @Override
    public RegisterResponse registerSuperAdmin(AuthRequest request, String superAdminRequestHeader) {
        validateSuperAdminHeader(superAdminRequestHeader);
        validateUsernameNotExists(request.getUsername());

        UserAccount userAccount = toUserAccount(request.getUsername(), request.getPassword(), UserRole.ROLE_SUPER_ADMIN);
        userService.createUser(userAccount);

        return toRegisterResponse(userAccount);
    }

    @Override
    public LoginResponse login(AuthRequest request) {
    
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken
        );

        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String generatedToken = jwtService.generateToken(userAccount);

        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .token(generatedToken)
                .roles(String.valueOf(userAccount.getRole()))
                .build();
    }

    private void validateUsernameNotExists(String username) {
        if (userService.getByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
    }

    private void validateSuperAdminHeader(String superAdminRequestHeader) {
        if (!superAdminHeaderKey.equals(superAdminRequestHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ERROR INVALID SUPER_ADMIN HEADER");
        }
    }

    private UserAccount toUserAccount(String username, String password, UserRole role) {
        return UserAccount.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

    private RegisterResponse toRegisterResponse(UserAccount userAccount) {
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(String.valueOf(userAccount.getRole()))
                .build();
    }

    private UserAccount manualValidateCredentialsAndGetUserAccount(AuthRequest request) {
        UserAccount userAccount = userService.getByUsername(request.getUsername());

        if (userAccount != null){
            boolean isValid = passwordEncoder.matches(request.getPassword(), userAccount.getPassword());

            if (isValid){
                return userAccount;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}

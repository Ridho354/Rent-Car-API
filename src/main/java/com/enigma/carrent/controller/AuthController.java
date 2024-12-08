package com.enigma.carrent.controller;

import com.enigma.carrent.constant.Constant;
import com.enigma.carrent.dto.request.AuthRequest;
import com.enigma.carrent.dto.request.RegisterCustomerRequest;
import com.enigma.carrent.dto.response.LoginResponse;
import com.enigma.carrent.dto.response.RegisterResponse;
import com.enigma.carrent.service.AuthService;
import com.enigma.carrent.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API_AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Success", response);
    }

    // PreAuthorize ini adalah annotation yang kita gunakan untuk memberikan aturan keamanan sebelum
    // sebuah method dijalankan
    @PreAuthorize("hasRole('SUPER_ADMIN')") // hasRole otomatis akan menambahkan prefix "ROLE_"
//    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')") // tidak menambahkan prefix apapun
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerAdmin(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Success", response);
    }

    @PostMapping("/register-super-admin")
    public ResponseEntity<?> registerSuperAdmin(
            @RequestBody AuthRequest request,
            @RequestHeader(name = "Super-Admin-Header-Key") String superAdminRequestHeader
    ) {
        RegisterResponse response = authService.registerSuperAdmin(request, superAdminRequestHeader);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Success", response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse response = authService.login(authRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Login Success", response);
    }
}

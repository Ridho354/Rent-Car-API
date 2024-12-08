package com.enigma.carrent.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCustomerRequest {
    private String username;
    private String password;
    private String email;
    private String nik;
    private String name;
    private String phoneNumber;
    private String drivingLicense;
    private String address;
}

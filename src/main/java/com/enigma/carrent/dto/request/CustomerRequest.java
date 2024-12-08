package com.enigma.carrent.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String nik;
    private String name;
    private String driving_license;
    private String phone;
    private String address;
}

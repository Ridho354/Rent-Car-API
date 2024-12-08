package com.enigma.carrent.dto.request;

import com.enigma.carrent.constant.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequest {
    private String lisensePlate;
    private String name;
    private String brand;
    private String model;
}
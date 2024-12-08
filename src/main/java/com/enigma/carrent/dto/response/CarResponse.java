package com.enigma.carrent.dto.response;

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
public class CarResponse {
    private String lisensePlate;
    private String name;
    private String brand;
    private String model;
    private Long price;
    private CarStatus status;
}
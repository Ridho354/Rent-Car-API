package com.enigma.carrent.dto.request;

import com.enigma.carrent.constant.CarStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CarStatusUpdate {
    public CarStatus status;
}

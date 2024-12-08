package com.enigma.carrent.constant;

import com.enigma.carrent.entity.Car;

public enum CarStatus {
   AVAILABLE("Tersedia"),
   RENTED("Dalam Rental"),
   MAINTENANCE("Dalam Perbaikan");
    
    private final String value;
    
    CarStatus(String value) {
        this.value = value;
    }

    public static CarStatus fromValue(String value) {
        for (CarStatus carStatus : values()) {
            if (carStatus.value.equalsIgnoreCase(value)) {
                return carStatus;
            }
        }
        return null;
    }
}

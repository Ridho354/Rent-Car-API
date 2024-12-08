package com.enigma.carrent.constant;

public enum UserRole {
    ROLE_CUSTOMER("Pelanggan"),
    ROLE_ADMIN("Karyawan"),
    ROLE_SUPER_ADMIN("Pemilik");


    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public static UserRole fromValue(String value) {
        for (UserRole userRole : values()) {
            if (userRole.value.equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        return null;
    }
}


package com.enigma.carrent.constant;

public enum RentTransactionStatus {
    DRAFT("DRAFT"),
    PENDING("PROSES"),
    PAID("DIBAYAR"),
    RENT("DALAM RENTAL"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");
    
    
    private final String value;
    
    RentTransactionStatus(String value) {
        this.value = value;
    }

    public static RentTransactionStatus fromValue(String value) {
        for (RentTransactionStatus rentTransactionStatus : values()) {
            if (rentTransactionStatus.value.equalsIgnoreCase(value)) {
                return rentTransactionStatus;
            }
        }
        return null;
    }
}

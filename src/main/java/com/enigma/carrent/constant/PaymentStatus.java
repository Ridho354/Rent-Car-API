package com.enigma.carrent.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    SETTLEMENT("settlement"),
    PENDING("pending"),
    DENY("deny"),
    CANCEL("cancel"),
    EXPIRE("expire"),
    FAILURE("failure"),
    CAPTURE("capture");

    private final String status;

    public static PaymentStatus fromStatus(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.status.equalsIgnoreCase(status)) {
                    return paymentStatus;
            }
        }
        return null;
    }
}

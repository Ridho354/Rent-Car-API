package com.enigma.carrent.dto.request;

import com.enigma.carrent.constant.RentTransactionStatus;

import lombok.Getter;

@Getter
public class RentStatusUpdate {
    private RentTransactionStatus status;
}

package com.enigma.carrent.dto.request;

import com.enigma.carrent.constant.RentTransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RentStatusUpdate {
    private RentTransactionStatus status;
}

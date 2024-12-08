package com.enigma.carrent.dto.response;

import java.time.LocalDate;
import java.util.Date;

import com.enigma.carrent.constant.RentTransactionStatus;

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
public class RentTransactionResponse {
    private String id;
    private String nik;
    private String carId;
    private Date rentalStartDate;
    private Date rentalEndDate;
    private Long totalPrice;
    private RentTransactionStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
package com.enigma.carrent.dto.request;

import java.time.LocalDate;
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
public class RentTransactionRequest {
    private String nik;
    private String carId;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private Long totalPrice;
    private RentTransactionStatus status;
}

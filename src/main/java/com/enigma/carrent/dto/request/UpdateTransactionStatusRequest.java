package com.enigma.carrent.dto.request;

import com.enigma.carrent.constant.RentTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionStatusRequest {
    private RentTransactionStatus transactionStatus;
}

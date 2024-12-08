package com.enigma.carrent.dto.request;
import com.enigma.carrent.constant.RentTransactionStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UpdateTransactionRequest {
    public RentTransactionStatus status;
}

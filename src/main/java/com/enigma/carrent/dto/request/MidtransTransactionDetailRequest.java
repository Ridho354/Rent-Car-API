package com.enigma.carrent.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidtransTransactionDetailRequest {
    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "gross_amount")
    private Long grossAmount;
}

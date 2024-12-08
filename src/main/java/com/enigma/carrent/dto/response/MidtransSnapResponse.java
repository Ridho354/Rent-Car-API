package com.enigma.carrent.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MidtransSnapResponse {
    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
}

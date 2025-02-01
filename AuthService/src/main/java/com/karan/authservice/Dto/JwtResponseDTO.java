package com.karan.authservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {

    private String accessToken;
    private String token;

}

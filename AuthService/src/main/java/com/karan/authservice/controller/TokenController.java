package com.karan.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.RefreshTokenRequestDTO;
import com.karan.authservice.service.RefreshTokenService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("auth/v1")
@AllArgsConstructor
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refreshAccessToken(
            @RequestBody RefreshTokenRequestDTO refreshDTO
    ) {
        JwtResponseDTO responseDTO = refreshTokenService.refreshAccessToken(refreshDTO);
        return ResponseEntity.ok(responseDTO);
    }

}

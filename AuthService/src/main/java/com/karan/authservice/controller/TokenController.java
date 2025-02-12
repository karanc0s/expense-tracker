package com.karan.authservice.controller;

import com.karan.authservice.Dto.AuthRequestDTO;
import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.RefreshTokenRequestDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.service.JwtService;
import com.karan.authservice.service.RefreshTokenService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("auth/v1")
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public TokenController(
            RefreshTokenService refreshTokenService
    ) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDTO> refreshAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        JwtResponseDTO responseDTO = refreshTokenService.refreshToken(refreshTokenRequestDTO.getToken());
        return ResponseEntity.ok(responseDTO);
    }

}

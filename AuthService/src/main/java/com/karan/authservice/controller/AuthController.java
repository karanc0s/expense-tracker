package com.karan.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karan.authservice.Dto.AuthRequestDTO;
import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("auth/v1")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login (
            @RequestBody AuthRequestDTO authRequestDTO
    ) {
        JwtResponseDTO responseDTO = authService.login(authRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register (
            @RequestBody UserInfoDTO authRequestDTO
    ) {
        JwtResponseDTO responseDTO = authService.register(authRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(
            @RequestHeader(value = "Authorization" , required = true) String token
    ){
        String userId = authService.validateToken(token);
        return ResponseEntity.ok(userId);
    }
}

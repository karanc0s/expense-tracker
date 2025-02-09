package com.karan.authservice.controller;

import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.RefreshToken;

import com.karan.authservice.service.AuthService;
import com.karan.authservice.service.JwtService;
import com.karan.authservice.service.RefreshTokenService;
import com.karan.authservice.service.UserDetailsIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(
            AuthService authService
    ) {

        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDTO> signUp(@RequestBody UserInfoDTO userInfoDTO) {
        JwtResponseDTO responseDTO =  authService.signUp(userInfoDTO);
        return new ResponseEntity<>(
                responseDTO,
                HttpStatus.CREATED
        );
    }
}

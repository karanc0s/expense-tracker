package com.karan.authservice.controller;

import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.RefreshToken;
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

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsIMPL userDetailsIMPL;

    @Autowired
    public AuthController(
            JwtService jwtService,
            RefreshTokenService refreshTokenService ,
            UserDetailsIMPL userDetailsIMPL
    ) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsIMPL = userDetailsIMPL;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserInfoDTO userInfoDTO){
        try{
            Boolean isSignUped = userDetailsIMPL.signUpUser(userInfoDTO);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.generateToken(userInfoDTO.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken).
                    token(refreshToken.getToken()).build(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

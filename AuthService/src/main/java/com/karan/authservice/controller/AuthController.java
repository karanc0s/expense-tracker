package com.karan.authservice.controller;

import com.karan.authservice.Dto.AuthRequestDTO;
import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/v1")
public class AuthController {

    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(
            AuthService authService,
            UserDetailsService userDetailsService
    ) {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDTO> signUp(@RequestBody UserInfoDTO userInfoDTO) {
        JwtResponseDTO responseDTO =  authService.signUp(userInfoDTO);
        return new ResponseEntity<>(
                responseDTO,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> logIn(@RequestBody AuthRequestDTO authRequestDTO) {
        JwtResponseDTO responseDTO = authService.logIn(authRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

//    public ResponseEntity<String> ping(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth != null && auth.isAuthenticated()){
//            String userId = userDetailsService.loadUserByUsername(auth.getName());
//        }
//    }

    @GetMapping("/health")
    public ResponseEntity<Boolean> checkHealth(){
        return ResponseEntity.ok(true);
    }
}

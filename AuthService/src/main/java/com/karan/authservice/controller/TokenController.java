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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;


@RestController
@RequestMapping("auth/v1")
public class TokenController {


    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final Producer<String , UserInfo> kafkaProducer;

    public TokenController(
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            Producer<String , UserInfo> kafkaProducer
    ) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> authenticateAndGetToken(@RequestBody AuthRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );
        
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(requestDTO.getUsername());
            return ResponseEntity.ok(JwtResponseDTO.builder()
                            .accessToken(jwtService.generateToken(requestDTO.getUsername()))
                            .token(refreshToken.getToken())
                    .build());
        }else{
            throw  new RuntimeException("Exception in User Service");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshToken -> refreshTokenService.verifyExpiryDate(refreshToken))
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken())
                            .build();
                }).orElseThrow(
                        () -> new RuntimeException("Refresh token not found")
                );
    }

    @GetMapping("/send")
    public void sendEvent(){
        UserInfo newUser = new UserInfo(
                "sdfsdf434j3k4j34",
                "KarandeepSingh",
                "35342323fdf#23",
                new HashSet<>()
        );
        ProducerRecord<String , UserInfo> record = new ProducerRecord<>("T11TEST","User_Event" ,newUser);
        kafkaProducer.send(record , (metadata , exception)->{
            if (exception != null) {
                System.err.println("Error sending message to Kafka: " + exception.getMessage());
            } else {
                System.out.println("Message sent to Kafka, offset: " + metadata.offset());
            }
        });
    }

}

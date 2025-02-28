package com.karan.authservice.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karan.authservice.Dto.AuthRequestDTO;
import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserCreds;
import com.karan.authservice.exception.AccessTokenExpiredException;
import com.karan.authservice.exception.AlreadyExistsException;
import com.karan.authservice.exception.BadCredentialsException;
import com.karan.authservice.exception.InvalidTokenException;
import com.karan.authservice.exception.ResourceNotFound;
import com.karan.authservice.repository.UserCredRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    
    private final UserCredRepository userCredRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final ProducerService producerService;


    public JwtResponseDTO login(AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();
    
        

        UserCreds creds = userCredRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFound("user not found")
        );
        if (!password.equals(creds.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }

        String accessToken = jwtService.generateAccessToken(creds.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(creds);

        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public JwtResponseDTO register(UserInfoDTO userInfoDTO) {
        String username = userInfoDTO.getUsername();
        String password = userInfoDTO.getPassword();

        Optional<UserCreds> optionalUserCreds = userCredRepository.findByUsername(username);
        if(optionalUserCreds.isPresent()){
            throw new AlreadyExistsException("username already exists");
        }

        UserCreds creds = new UserCreds();
        creds.setUsername(username);
        creds.setPassword(password);
        creds.setUserId(UUID.randomUUID().toString());

        creds = userCredRepository.save(creds);

        UserInfoDTO eventDTO = UserInfoDTO.builder()
                .userId(creds.getUserId())
                .username(creds.getUsername())
                .email(userInfoDTO.getEmail())
                .firstname(userInfoDTO.getFirstname())
                .lastname(userInfoDTO.getLastname())
                .phoneNumber(userInfoDTO.getPhoneNumber())
                .build();

        producerService.sendUserEvent(eventDTO);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(creds);
        String accessToken = jwtService.generateAccessToken(creds.getUsername());

        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


    public String validateToken(String token) {
        if(jwtService.isTokenExpired(token)){
            throw new AccessTokenExpiredException("Token is expired");
        }
        String tokenType = jwtService.extractTokenType(token);
        System.out.println("TOKEN TYPE: " + tokenType);
        if(!tokenType.equals("ACCESS_TOKEN")){
            throw new InvalidTokenException("Invalid token");
        }

        String username = jwtService.extractUserName(token);
        Optional<UserCreds> optionalUserCreds = userCredRepository.findByUsername(username);
        if(optionalUserCreds.isEmpty()){
            throw new ResourceNotFound("Not Found.... ISSUE!!!!");
        }
        return optionalUserCreds.get().getUserId();
    }

    

}

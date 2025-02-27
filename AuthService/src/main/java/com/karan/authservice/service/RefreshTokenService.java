package com.karan.authservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.RefreshTokenRequestDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserCreds;
import com.karan.authservice.exception.ResourceNotFound;
import com.karan.authservice.repository.RefreshTokenRepository;
import com.karan.authservice.repository.UserCredRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserCredRepository userCredRepository;
    private final JwtService jwtService;

    public JwtResponseDTO refreshAccessToken(RefreshTokenRequestDTO refreshDTO){

        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(refreshDTO.getRefreshToken());
        if(optionalRefreshToken.isEmpty()){
            throw new ResourceNotFound("Refresh token not found");
        }
        RefreshToken refreshToken = optionalRefreshToken.get();
        if(refreshToken.getIsRevoked()){
            throw new RuntimeException("Refresh token is revoked");
        }

        UserCreds creds = userCredRepository.findByUserId(refreshToken.getUserID()).orElseThrow(
                () -> new RuntimeException("User credentials not found")
        );
        String newRefresh = jwtService.generateRefreshToken(creds.getUsername());

        refreshToken.setRefreshToken(newRefresh);
        refreshToken.setIsRevoked(false);

        refreshToken = refreshTokenRepository.save(refreshToken);

        String accessToken = jwtService.generateAccessToken(creds.getUsername());

        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public RefreshToken createRefreshToken(UserCreds userCreds){
        String token = jwtService.generateRefreshToken(userCreds.getUsername());
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByuserID(userCreds.getUserId());
        RefreshToken refreshToken = new RefreshToken();

        if(optionalRefreshToken.isPresent()){
            refreshToken = optionalRefreshToken.get();
        }

        refreshToken.setRefreshToken(token);
        refreshToken.setIsRevoked(false);
        refreshToken.setUserID(userCreds.getUserId());

        return refreshTokenRepository.save(refreshToken);
    }
}

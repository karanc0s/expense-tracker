package com.karan.authservice.service;

import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.exception.ExpiredTokenException;
import com.karan.authservice.repository.RefreshTokenRepository;
import com.karan.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository, JwtService jwtService,
            UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public RefreshToken verifyExpiryDate(RefreshToken refreshToken) {
        // if token is expired then, this will give -ve value
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredTokenException("Expired refresh token. Please Login Again");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken saveToken(UserInfo userInfo, String refreshToken) {
        if(userInfo == null || refreshToken == null) {
            throw new RuntimeException("User or refresh token is null");
        }
        RefreshToken token = RefreshToken.builder()
                .userInfo(userInfo)
                .token(refreshToken)
                .expiryDate(Instant.now().plusMillis(600000))
                .build();

        return refreshTokenRepository.save(token);
    }

    public JwtResponseDTO refreshToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateAccessToken(userInfo.getUsername());
                    saveToken(userInfo, accessToken);
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }).orElseThrow(
                        () -> new RuntimeException("Refresh token not found")
                );

    }
}

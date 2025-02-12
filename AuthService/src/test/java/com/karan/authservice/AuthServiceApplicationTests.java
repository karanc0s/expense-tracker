package com.karan.authservice;

import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.repository.RefreshTokenRepository;
import com.karan.authservice.repository.UserRepository;
import com.karan.authservice.service.AuthService;
import com.karan.authservice.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthServiceApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private RefreshTokenRepository refreshTokenRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private AuthService authService;

	private UserInfo testUser;
	private RefreshToken refreshToken;

	@BeforeEach
	void setUp(){
		testUser = new UserInfo("1", "testUser", "encodedPassword", new HashSet<>());
		refreshToken = RefreshToken.builder()
				.token(jwtService.generateRefreshToken(testUser.getUsername()))
				.userInfo(testUser)
				.expiryDate(Instant.now().plusMillis(1000L * 60 * 60 * 24 ))
				.build();
	}


//	@Test
//	void testUserSignup() {
//		UserInfoDTO userInfoDTO = new UserInfoDTO("testUser", "password");
//		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
//		when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
//		when(userRepository.save(any())).thenReturn(testUser);
//		when(jwtService.generateAccessToken(any(), eq(true))).thenReturn("mockAccessToken");
//		when(refreshTokenRepository.save(any())).thenReturn(testRefreshToken);
//
//		JwtResponseDTO response = authService.signUp(userInfoDTO);
//
//		assertNotNull(response);
//		assertEquals("mockAccessToken", response.getAccessToken());
//		assertEquals(testRefreshToken.getToken(), response.getRefreshToken());
//	}
//
//	@Test
//	void testUserLogin() {
//		AuthRequestDTO requestDTO = new AuthRequestDTO("testUser", "password");
//		when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("testUser", "password"));
//		when(userRepository.findByUsername(any())).thenReturn(Optional.of(testUser));
//		when(jwtService.generateToken(any(), eq(true))).thenReturn("mockAccessToken");
//		when(jwtService.generateToken(any(), eq(false))).thenReturn("mockRefreshToken");
//		when(refreshTokenRepository.save(any())).thenReturn(testRefreshToken);
//
//		JwtResponseDTO response = authService.logIn(requestDTO);
//
//		assertNotNull(response);
//		assertEquals("mockAccessToken", response.getAccessToken());
//		assertEquals("mockRefreshToken", response.getRefreshToken());
//	}
//
//	@Test
//	void testRefreshToken() {
//		when(refreshTokenRepository.findByToken(any())).thenReturn(Optional.of(testRefreshToken));
//		when(jwtService.generateToken(any(), eq(true))).thenReturn("mockNewAccessToken");
//
//		String newAccessToken = authService.refreshToken(testRefreshToken.getToken());
//
//		assertNotNull(newAccessToken);
//		assertEquals("mockNewAccessToken", newAccessToken);
//	}

}

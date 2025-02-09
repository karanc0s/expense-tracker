package com.karan.authservice.service;

import com.karan.authservice.Dto.AuthRequestDTO;
import com.karan.authservice.Dto.JwtResponseDTO;
import com.karan.authservice.Dto.UserInfoDTO;
import com.karan.authservice.entities.RefreshToken;
import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.exception.UserAlreadyExistsException;
import com.karan.authservice.exception.UserNotFoundException;
import com.karan.authservice.repository.RefreshTokenRepository;
import com.karan.authservice.repository.UserRepository;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final Producer<String, UserInfo> producer;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Autowired
    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            Producer<String, UserInfo> producer,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.producer = producer;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public JwtResponseDTO logIn(AuthRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );
        if(!authentication.isAuthenticated()){
            throw new UserNotFoundException("Invalid username or password");
        }

        String refreshToken = jwtService.generateToken(requestDTO.getUsername() , 86400000L);
        String accessToken = jwtService.generateToken(requestDTO.getUsername());
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(requestDTO.getUsername());

        if(optionalUserInfo.isEmpty()){
            throw new UserNotFoundException("Unexpected Error");
        }

        RefreshToken refresh = RefreshToken.builder()
                .token(refreshToken)
                .userInfo(optionalUserInfo.get())
                .expiryDate(Instant.now().plusMillis(86400000L))
                .build();

        refreshTokenRepository.save(refresh);

        return new JwtResponseDTO.Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtResponseDTO signUp(UserInfoDTO userInfoDTO){
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(fetchUser(userInfoDTO.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User Already Exist");
        }
        String userId = UUID.randomUUID().toString();
        UserInfo info = new UserInfo(
                userId,
                userInfoDTO.getUsername(),
                userInfoDTO.getPassword(),
                new HashSet<>()
        );
        userRepository.save(info);
        RefreshToken refreshToken = createRefreshToken(info);
        String accessToken = jwtService.generateToken(info.getUsername());

        sendEvent(userInfoDTO);

        return new JwtResponseDTO.Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    private Optional<UserInfo> fetchUser(String username) {
        return userRepository.findByUsername(username);
    }


    private void sendEvent(UserInfoDTO userInfoDTO){
        UserInfo newUser = new UserInfo(
                "sdfsdf434j3k4j34",
                "KarandeepSingh",
                "35342323fdf#23",
                new HashSet<>()
        );
        ProducerRecord<String , UserInfo> record = new ProducerRecord<>("T11TEST","User_Event" ,newUser);
        producer.send(record , (metadata , exception)->{
            if (exception != null) {
                System.err.println("Error sending message to Kafka: " + exception.getMessage());
            } else {
                System.out.println("Message sent to Kafka, offset: " + metadata.offset());
            }
        });
    }

    private RefreshToken createRefreshToken(UserInfo userInfo){
        String refreshToken = UUID.randomUUID().toString();
        System.out.println(refreshToken);
        return RefreshToken.builder()
                .token(refreshToken)
                .userInfo(userInfo)
                .expiryDate(Instant.now().plusMillis(86400000L))
                .build();
    }

}

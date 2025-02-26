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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final Producer<String, UserInfoDTO> producer;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Autowired
    public AuthService(
            UserRepository userRepository,
            RefreshTokenService tokenService,
            PasswordEncoder passwordEncoder,
            Producer<String, UserInfoDTO> producer,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
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

        String refreshToken = jwtService.generateRefreshToken(requestDTO.getUsername());
        String accessToken = jwtService.generateAccessToken(requestDTO.getUsername());
        Optional<UserInfo> optionalUserInfo = userRepository.findByUsername(requestDTO.getUsername());

        if(optionalUserInfo.isEmpty()){
            throw new UserNotFoundException("Unexpected Error");
        }
        tokenService.saveToken(optionalUserInfo.get() , refreshToken);
        return new JwtResponseDTO.Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtResponseDTO signUp(UserInfoDTO infoDTO){
        infoDTO.setPassword(passwordEncoder.encode(infoDTO.getPassword()));
        if(fetchUser(infoDTO.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User Already Exist");
        }
        String userId = UUID.randomUUID().toString();
        UserInfo info = new UserInfo(
                userId,
                infoDTO.getUsername(),
                infoDTO.getPassword(),
                new HashSet<>()
        );
        info = userRepository.save(info);
        String refToken = jwtService.generateRefreshToken(info.getUsername());
        RefreshToken refreshToken = tokenService.saveToken(info , refToken);
        String accessToken = jwtService.generateAccessToken(info.getUsername());


        UserInfoDTO eventDTO = UserInfoDTO.builder()
                .userId(info.getUserId())
                .username(info.getUsername())
                .firstname(infoDTO.getFirstname())
                .lastname(infoDTO.getLastname())
                .phoneNumber(infoDTO.getPhoneNumber())
                .email(infoDTO.getEmail())
                .build();

//        sendEvent(eventDTO);


        return new JwtResponseDTO.Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    private Optional<UserInfo> fetchUser(String username) {
        return userRepository.findByUsername(username);
    }

    public String validate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()){
            Optional<UserInfo> optionalUserInfo = fetchUser(auth.getName());
            if(optionalUserInfo.isEmpty()){
                throw new UserNotFoundException("Unexpected Error");
            }
            return optionalUserInfo.get().getUserId();
        }else{
            throw new BadCredentialsException("Bad credentials");
        }
    }
    // sending event
    private void sendEvent(UserInfoDTO userInfoDTO){


        ProducerRecord<String , UserInfoDTO> record = new ProducerRecord<>("T11TEST","USER_EVENT_CREATED" ,userInfoDTO);
        producer.send(record , (metadata , exception)->{
            if (exception != null) {
                System.err.println("Error sending message to Kafka: " + exception.getMessage());
            } else {
                System.out.println("Message sent to Kafka, offset: " + metadata.offset());
            }
        });
    }

}

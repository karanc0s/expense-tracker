package com.karan.userservice.service;

import org.springframework.stereotype.Service;

import com.karan.userservice.Dto.UserInfoDTO;
import com.karan.userservice.entities.UserInfo;
import com.karan.userservice.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserInfoDTO userInfoDTO) {
        if(userInfoDTO == null){
            throw new RuntimeException("userInfoDTO is null");
        }
        UserInfo userInfo = UserInfo.builder()
                .userId(userInfoDTO.getUserId())
                .email(userInfoDTO.getEmail())
                .firstName(userInfoDTO.getFirstname())
                .lastName(userInfoDTO.getLastname())
                .username(userInfoDTO.getUsername())
                .phoneNumber(userInfoDTO.getPhoneNumber())
                .profilePic("")
                .build();
        userRepository.save(userInfo);
    }

    public UserInfoDTO getUser(UserInfoDTO userInfoDTO){
        return null;
        // Optional<UserInfo> userInfoOptional = userRepository.findById(userInfoDTO.getUserId());
        // if(userInfoOptional.isEmpty()){
        //     throw new ResolutionException("User not found");
        // }
        // UserInfo userInfo = userInfoOptional.get();

        // return  UserInfoDTO.builder()
        //         .userId(userInfoDTO.getUserId())
        //         .email(userInfoDTO.getEmail())
        //         .firstname(userInfoDTO.getFirstname())
        //         .lastname(userInfoDTO.getLastname())
        //         .username(userInfoDTO.getUsername())
        //         .phoneNumber(userInfoDTO.getPhoneNumber())
        //         .email(userInfo.getEmail())
        //         .build();
    }

}

package com.karan.authservice.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.karan.authservice.entities.UserInfo;
import lombok.Builder;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO extends UserInfo {

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;


}

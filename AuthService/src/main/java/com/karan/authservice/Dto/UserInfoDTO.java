package com.karan.authservice.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO {

    private String userId;

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    private Long phoneNumber;

    private String email;

    private Set<String> roles;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String userId , String firstname, String lastname, String username, String password, Long phoneNumber, String email, Set<String> roles) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static Builder builder(){
        return new Builder();
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public static class Builder{
        private String userId;
        private String firstname;
        private String lastname;
        private String username;
        private String password;
        private Long phoneNumber;
        private String email;
        private Set<String> roles;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder roles(Set<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserInfoDTO build() {
            return new UserInfoDTO(userId ,firstname, lastname, username, password, phoneNumber, email , roles);
        }

    }
}

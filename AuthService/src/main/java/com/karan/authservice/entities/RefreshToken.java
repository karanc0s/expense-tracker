package com.karan.authservice.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "id" , referencedColumnName = "user_id")
    private UserInfo userInfo;

    public RefreshToken() {
    }

    public RefreshToken(
            Integer id,
            String token,
            Instant expiryDate,
            UserInfo userInfo
    ) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userInfo = userInfo;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    // toString() Method
    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                ", userInfo=" + (userInfo != null ? userInfo.getUserId() : null) +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private Integer id;
        private String token;
        private Instant expiryDate;
        private UserInfo userInfo;
        public Builder id(Integer id) {
            this.id = id;
            return this;
        }
        public Builder token(String token) {
            this.token = token;
            return this;
        }
        public Builder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }
        public Builder userInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }
        public RefreshToken build() {
            return new RefreshToken(id, token, expiryDate, userInfo);
        }
    }
}

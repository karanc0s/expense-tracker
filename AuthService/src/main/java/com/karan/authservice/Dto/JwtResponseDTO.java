package com.karan.authservice.Dto;


public class JwtResponseDTO {

    private String accessToken;
    private String refreshToken;

    // No-args Constructor
    public JwtResponseDTO() {
    }

    // All-args Constructor
    public JwtResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken() {
        return refreshToken;
    }

    public void setToken(String token) {
        this.refreshToken = token;
    }

    public static Builder builder(){
        return new Builder();
    }

    // Builder Pattern
    public static class Builder {
        private String accessToken;
        private String token;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String token) {
            this.token = token;
            return this;
        }

        public JwtResponseDTO build() {
            return new JwtResponseDTO(accessToken, token);
        }
    }

    // toString() Method (Optional for debugging)
    @Override
    public String toString() {
        return "JwtResponseDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", token='" + refreshToken + '\'' +
                '}';
    }

}

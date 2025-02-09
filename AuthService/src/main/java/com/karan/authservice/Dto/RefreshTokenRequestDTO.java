package com.karan.authservice.Dto;



public class RefreshTokenRequestDTO {

    private String refreshToken;

    public RefreshTokenRequestDTO() {}
    public RefreshTokenRequestDTO(
            String refreshToken
    ) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return refreshToken;
    }

    public void setToken(String token) {
        this.refreshToken = token;
    }

    public static class Builder{
        private String token;

        public Builder token(String token){
            this.token = token;
            return this;
        }

        public RefreshTokenRequestDTO build(){
            return new RefreshTokenRequestDTO(token);
        }
    }
}

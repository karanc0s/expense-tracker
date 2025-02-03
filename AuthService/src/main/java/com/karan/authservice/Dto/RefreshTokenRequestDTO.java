package com.karan.authservice.Dto;



public class RefreshTokenRequestDTO {

    private String token;

    public RefreshTokenRequestDTO() {}
    public RefreshTokenRequestDTO(
            String token
    ) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

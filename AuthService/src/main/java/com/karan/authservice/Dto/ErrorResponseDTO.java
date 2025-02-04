package com.karan.authservice.Dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponseDTO {

    private String apiPath;
    private HttpStatus httpStatus;
    private String errorMessage;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String apiPath, HttpStatus httpStatus, String errorMessage, LocalDateTime timestamp) {
        this.apiPath = apiPath;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ErrorResponseDTO() {
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String apiPath;
        private HttpStatus httpStatus;
        private String errorMessage;
        private LocalDateTime timestamp;

        public Builder apiPath(String apiPath) {
            this.apiPath = apiPath;
            return this;
        }
        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }
        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        public ErrorResponseDTO build() {
            return new ErrorResponseDTO(apiPath, httpStatus, errorMessage, timestamp);
        }
    }
}

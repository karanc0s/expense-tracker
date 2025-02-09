package com.karan.authservice.exception;


import com.karan.authservice.Dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {




    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenExpiredException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse =  ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse =  ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse =  ErrorResponseDTO.builder()
                .apiPath(request.getDescription(false))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    //    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorResponseDTO errorResponse =  ErrorResponseDTO.builder()
//                .apiPath(request.getDescription(false))
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .errorMessage(ex.getMessage())
//                .timestamp(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }




}

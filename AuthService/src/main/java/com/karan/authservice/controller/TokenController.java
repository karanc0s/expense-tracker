package com.karan.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1")
public class TokenController {

    @PostMapping("/login")
    public void authenticateAndGetToken(){}

    @PostMapping("/refreshToken")
    public void refreshToken(){}
}

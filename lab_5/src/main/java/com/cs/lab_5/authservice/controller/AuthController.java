package com.cs.lab_5.authservice.controller;


import com.cs.lab_5.authservice.payload.JwtAuthenticationResponse;
import com.cs.lab_5.authservice.payload.LoginRequest;
import com.cs.lab_5.authservice.payload.SignUpRequest;
import com.cs.lab_5.authservice.payload.SignupResponse;
import com.cs.lab_5.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest.username(), loginRequest.password());
    }

    @PostMapping("/verify/{code}")
    public JwtAuthenticationResponse verifyCode(@PathVariable String code) {
        return authService.verify(code);
    }

    @PostMapping("/register")
    public SignupResponse createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerNewUser(signUpRequest);
    }
}

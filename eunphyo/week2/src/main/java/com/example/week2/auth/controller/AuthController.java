package com.example.week2.auth.controller;

import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import com.example.week2.auth.dto.LoginRequest;
import com.example.week2.auth.dto.SignupRequest;
import com.example.week2.auth.dto.TokenResponse;
import com.example.week2.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs{

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<TokenResponse>> signup(
            @Valid @RequestBody SignupRequest request
    ) {

        TokenResponse response = authService.signup(request);

        return ResponseEntity
                .status(SuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
    ) {
        TokenResponse response = authService.login(request);

        return ResponseEntity
                .status(SuccessCode.LOGIN_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, response));
    }
}
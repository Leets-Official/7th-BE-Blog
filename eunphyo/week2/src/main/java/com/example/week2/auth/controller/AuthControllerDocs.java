package com.example.week2.auth.controller;

import com.example.week2.auth.dto.LoginRequest;
import com.example.week2.auth.dto.SignupRequest;
import com.example.week2.auth.dto.TokenResponse;
import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.global.swagger.ApiErrorCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원가입 및 로그인 API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입", description = "이메일 회원가입을 진행합니다.")
    @ApiErrorCodeExample({
            ErrorCode.EMAIL_ALREADY_EXISTS,
            ErrorCode.NAME_ALREADY_EXISTS
    })
    @PostMapping("/signup")
    ResponseEntity<ApiResponse<TokenResponse>> signup(
            @Valid @RequestBody SignupRequest request
    );


    @Operation(summary = "로그인", description = "이메일 로그인 후 Access Token과 Refresh Token을 발급합니다.")
    @ApiErrorCodeExample({
            ErrorCode.USER_NOT_FOUND,
            ErrorCode.INVALID_PASSWORD
    })
    ResponseEntity<ApiResponse<TokenResponse>> login(
            @RequestBody LoginRequest request
    );
}
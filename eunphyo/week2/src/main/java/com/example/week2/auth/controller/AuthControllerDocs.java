package com.example.week2.auth.controller;

import com.example.week2.auth.dto.AuthRequest;
import com.example.week2.auth.dto.AuthResponse;
import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.global.swagger.ApiErrorCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "회원가입 및 로그인 API")
public interface AuthControllerDocs {

    @Operation(summary = "회원가입", description = "이메일 회원가입을 진행합니다.")
    @ApiErrorCodeExample({
            ErrorCode.EMAIL_ALREADY_EXISTS,
    })
    @PostMapping("/signup")
    ApiResponse<Void> signup(
            @Valid @RequestBody AuthRequest.SignupRequest request
    );


    @Operation(summary = "로그인", description = "이메일 로그인 후 Access Token과 Refresh Token을 발급합니다.")
    @ApiErrorCodeExample({
            ErrorCode.USER_NOT_FOUND,
            ErrorCode.INVALID_PASSWORD
    })
    @PostMapping("/login")
    ApiResponse<AuthResponse.AccessToken> login(
           @Valid @RequestBody AuthRequest.LoginRequest request,
            HttpServletResponse servletResponse
    );

    @Operation(summary = "토큰 재발급", description = "Token을 재발급합니다.")
    @ApiErrorCodeExample({
            ErrorCode.INVALID_REFRESH_TOKEN
    })
    @PostMapping("/reissue")
    ApiResponse<AuthResponse.AccessToken> reissue(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse response
            );

    @Operation(summary = "카카오 로그인 페이지 이동", description = "카카오 OAuth 인가 페이지로 리다이렉트합니다.")
    @GetMapping("/kakao/login")
    ResponseEntity<Void> redirectKakaoLogin();

    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드를 받아 로그인 처리 후 Access Token을 발급합니다.")
    @ApiErrorCodeExample({
            ErrorCode.KAKAO_TOKEN_REQUEST_FAILED,
            ErrorCode.KAKAO_USER_INFO_REQUEST_FAILED
    })
    @GetMapping("/kakao/callback")
    ApiResponse<AuthResponse.AccessToken> kakaoCallback(
            @RequestParam String code,
            HttpServletResponse servletResponse
    );
}
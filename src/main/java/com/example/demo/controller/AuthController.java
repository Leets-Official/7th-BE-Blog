package com.example.demo.controller;

import com.example.demo.domain.auth.dto.AuthTokenResponse;
import com.example.demo.domain.auth.dto.LoginRequest;
import com.example.demo.domain.auth.dto.SignUpRequest;
import com.example.demo.domain.auth.dto.SignUpResponse;
import com.example.demo.domain.auth.dto.TokenRefreshRequest;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 닉네임, 비밀번호로 회원가입을 진행합니다.")
    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("SIGN_UP_SUCCESS", "회원가입 성공", authService.signUp(request)));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 access token과 refresh token을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthTokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("LOGIN_SUCCESS", "로그인 성공", authService.login(request))
        );
    }

    @Operation(summary = "토큰 재발급", description = "refresh token으로 새로운 access token과 refresh token을 발급받습니다.")
    @PostMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<AuthTokenResponse>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("TOKEN_REFRESH_SUCCESS", "토큰 재발급 성공", authService.refresh(request))
        );
    }
}

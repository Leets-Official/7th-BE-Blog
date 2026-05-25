package com.leets.blog.domain.auth.controller;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.domain.auth.dto.LoginRequest;
import com.leets.blog.domain.auth.dto.LoginResponse;
import com.leets.blog.domain.auth.dto.SignupRequest;
import com.leets.blog.domain.auth.dto.SignupResponse;
import com.leets.blog.domain.auth.dto.TokenReissueRequest;
import com.leets.blog.domain.auth.dto.TokenReissueResponse;
import com.leets.blog.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    @Operation(summary = "이메일 회원가입", description = "이메일, 비밀번호, 닉네임으로 회원가입합니다.")
    public ApiResponse<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
        return ApiResponse.onSuccess(authService.signup(request));
    }

    @PostMapping("/login")
    @Operation(summary = "이메일 로그인", description = "이메일과 비밀번호로 로그인하고 토큰을 발급받습니다.")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.onSuccess(authService.login(request));
    }

    @PostMapping("/auth/refresh")
    @Operation(summary = "액세스 토큰 재발급", description = "리프레시 토큰으로 새 액세스 토큰을 발급받습니다.")
    public ApiResponse<TokenReissueResponse> reissueAccessToken(@RequestBody @Valid TokenReissueRequest request) {
        return ApiResponse.onSuccess(authService.reissueAccessToken(request));
    }

    @GetMapping("/oauth/kakao/login")
    @Operation(summary = "카카오 로그인 페이지 이동", description = "카카오 인가 코드 발급 페이지로 리다이렉트합니다.")
    public ResponseEntity<Void> redirectToKakaoLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(authService.getKakaoAuthorizationUrl()))
                .build();
    }

    @GetMapping("/oauth/kakao/callback")
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드로 로그인하고 서비스 토큰을 발급받습니다.")
    public ApiResponse<LoginResponse> kakaoCallback(@RequestParam String code) {
        return ApiResponse.onSuccess(authService.kakaoLogin(code));
    }
}

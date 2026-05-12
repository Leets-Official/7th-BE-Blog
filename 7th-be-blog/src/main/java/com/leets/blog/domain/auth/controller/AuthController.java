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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

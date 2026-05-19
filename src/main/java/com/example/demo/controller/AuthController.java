package com.example.demo.controller;

import com.example.demo.domain.auth.dto.KakaoLoginResponse;
import com.example.demo.domain.auth.dto.LoginResponse;
import com.example.demo.domain.auth.dto.LoginRequest;
import com.example.demo.domain.auth.dto.SignUpRequest;
import com.example.demo.domain.auth.dto.SignUpResponse;
import com.example.demo.domain.auth.dto.TokenRefreshRequest;
import com.example.demo.domain.auth.dto.TokenRefreshResponse;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.auth.service.KakaoOAuthService;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoOAuthService kakaoOAuthService;

    @Operation(summary = "회원가입", description = "이메일, 닉네임, 비밀번호로 회원가입을 진행합니다.")
    @PostMapping("/auth")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("SIGN_UP_SUCCESS", "회원가입 성공", authService.signUp(request)));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 access token과 refresh token을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("LOGIN_SUCCESS", "로그인 성공", authService.login(request))
        );
    }

    @Operation(summary = "토큰 재발급", description = "refresh token으로 새로운 access token과 refresh token을 발급받습니다.")
    @PostMapping("/auth/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("TOKEN_REFRESH_SUCCESS", "토큰 재발급 성공", authService.refresh(request))
        );
    }

    @Operation(summary = "카카오 로그인 시작", description = "브라우저를 카카오 로그인 화면으로 리다이렉트합니다.")
    @GetMapping("/auth/kakao")
    public ResponseEntity<Void> redirectToKakao() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(kakaoOAuthService.buildAuthorizationUri()))
                .build();
    }

    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드를 받아 회원가입 또는 로그인을 처리한 뒤 서비스 토큰을 발급합니다.")
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<ApiResponse<KakaoLoginResponse>> kakaoCallback(
            @Parameter(description = "카카오에서 전달한 인가 코드")
            @RequestParam(required = false) String code,
            @Parameter(description = "카카오 로그인 실패 시 전달되는 에러 코드")
            @RequestParam(required = false) String error,
            @Parameter(description = "카카오 로그인 실패 상세 설명")
            @RequestParam(required = false, name = "error_description") String errorDescription
    ) {
        if (error != null) {
            String message = (errorDescription == null || errorDescription.isBlank())
                    ? "카카오 로그인이 취소되었거나 실패했습니다."
                    : "카카오 로그인이 실패했습니다. 사유: " + errorDescription;
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_LOGIN_FAILED", message);
        }

        if (code == null || code.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_AUTH_CODE_MISSING", "카카오 인가 코드가 전달되지 않았습니다.");
        }

        return ResponseEntity.ok(
                ApiResponse.success("KAKAO_LOGIN_SUCCESS", "카카오 로그인 성공", kakaoOAuthService.login(code))
        );
    }
}

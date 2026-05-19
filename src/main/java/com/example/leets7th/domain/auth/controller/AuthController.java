package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.LoginRequest;
import com.example.leets7th.domain.auth.dto.SignUpRequest;
import com.example.leets7th.domain.auth.dto.TokenResponse;
import com.example.leets7th.domain.auth.service.AuthService;
import com.example.leets7th.domain.auth.service.KakaoOAuthService;
import com.example.leets7th.global.common.ApiResponse;
import com.example.leets7th.global.util.CookieProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private static final String ACCESS_TOKEN_COOKIE = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    private final AuthService authService;
    private final KakaoOAuthService kakaoOAuthService;
    private final CookieProvider cookieProvider;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest request,
                                                    HttpServletResponse response) {
        TokenResponse tokens = authService.login(request);
        cookieProvider.addCookie(response, ACCESS_TOKEN_COOKIE, tokens.accessToken(), Duration.ofHours(1));
        cookieProvider.addCookie(response, REFRESH_TOKEN_COOKIE, tokens.refreshToken(), Duration.ofDays(14));
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse response) {
        String newAccessToken = authService.refresh(refreshToken);
        cookieProvider.addCookie(response, ACCESS_TOKEN_COOKIE, newAccessToken, Duration.ofHours(1));
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        cookieProvider.deleteCookie(response, ACCESS_TOKEN_COOKIE);
        cookieProvider.deleteCookie(response, REFRESH_TOKEN_COOKIE);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/kakao")
    public ResponseEntity<Void> kakaoLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", kakaoOAuthService.getKakaoLoginUrl())
                .build();
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<ApiResponse<Void>> kakaoCallback(
            @RequestParam String code,
            HttpServletResponse response) {
        TokenResponse tokens = kakaoOAuthService.kakaoLogin(code);
        cookieProvider.addCookie(response, ACCESS_TOKEN_COOKIE, tokens.accessToken(), Duration.ofHours(1));
        cookieProvider.addCookie(response, REFRESH_TOKEN_COOKIE, tokens.refreshToken(), Duration.ofDays(14));
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

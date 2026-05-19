package com.example.leets7th.domain.auth.controller;

import com.example.leets7th.domain.auth.dto.LoginRequest;
import com.example.leets7th.domain.auth.dto.SignUpRequest;
import com.example.leets7th.domain.auth.dto.TokenResponse;
import com.example.leets7th.domain.auth.service.AuthService;
import com.example.leets7th.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest request,
                                                    HttpServletResponse response) {
        TokenResponse tokens = authService.login(request);
        addCookie(response, ACCESS_TOKEN_COOKIE, tokens.accessToken(), Duration.ofHours(1));
        addCookie(response, REFRESH_TOKEN_COOKIE, tokens.refreshToken(), Duration.ofDays(14));
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refresh(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse response) {
        String newAccessToken = authService.refresh(refreshToken);
        addCookie(response, ACCESS_TOKEN_COOKIE, newAccessToken, Duration.ofHours(1));
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        deleteCookie(response, ACCESS_TOKEN_COOKIE);
        deleteCookie(response, REFRESH_TOKEN_COOKIE);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    private void addCookie(HttpServletResponse response, String name, String value, Duration maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ZERO)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}

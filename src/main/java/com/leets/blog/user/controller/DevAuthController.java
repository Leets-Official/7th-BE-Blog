package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.security.CookieUtils;
import com.leets.blog.security.JwtProperties;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth (Dev)", description = "개발 환경 전용 인증 API")
public class DevAuthController {

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @GetMapping("/kakao/callback")
    @Operation(summary = "카카오 로그인 콜백 (테스트용)", description = "카카오 인가 코드를 받아 로그인 처리합니다.")
    public ResponseEntity<BaseResponse<AuthResponse.Login>> kakaoCallback(
            @RequestParam String code,
            HttpServletResponse response
    ) {
        AuthResponse.Login loginResponse = authService.kakaoLogin(new AuthRequest.KakaoLogin(code));
        response.addHeader(HttpHeaders.SET_COOKIE,
                CookieUtils.createCookie(
                        jwtProperties.getAccessCookieName(),
                        loginResponse.getAccessToken(),
                        jwtProperties.getAccessTokenExpirationSeconds(),
                        jwtProperties
                ).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                CookieUtils.createCookie(
                        jwtProperties.getRefreshCookieName(),
                        loginResponse.getRefreshToken(),
                        jwtProperties.getRefreshTokenExpirationSeconds(),
                        jwtProperties
                ).toString());
        return ResponseEntity.ok(BaseResponse.ok(loginResponse));
    }
}

package com.example.leets_project.domain.auth.web.controller;

import com.example.leets_project.common.response.GlobalResponse;
import com.example.leets_project.common.response.SuccessCode;
import com.example.leets_project.common.security.jwt.JwtProperties;
import com.example.leets_project.common.util.CookieUtil;
import com.example.leets_project.domain.auth.service.AuthService;
import com.example.leets_project.domain.auth.web.dto.KakaoLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoOAuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final JwtProperties jwtProperties;

    @Operation(summary = "KakaoOAuth 로그인 API", description = "카카오 인증 서버로부터 Redirect 되는 Callback API")
    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<GlobalResponse> kakaoCallback(@RequestParam String code, HttpServletResponse response) {
        AuthService.LoginResult result = authService.loginWithKakao(new KakaoLoginRequest(code));

        cookieUtil.addRefreshTokenCookie(
                response,
                result.refreshToken(), // xss 공격 방어 위해 쿠키에 적재(스크립트 접근 불가)
                jwtProperties.getRefreshTokenExpirationMillis()
        );

        return GlobalResponse.onSuccess(SuccessCode.AUTH_LOGIN, result.loginResponse());
    }
}
package com.example.blog.domain.auth.controller;

import com.example.blog.domain.auth.dto.TokenResponse;
import com.example.blog.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "소셜 로그인", description = "카카오 OAuth 로그인")
@RestController
@RequestMapping("/oauth/kakao")
@RequiredArgsConstructor
public class OAuthController {

    private final AuthService authService;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.authorize-uri}")
    private String authorizeUri;

    @Value("${kakao.frontend-redirect-uri}")
    private String frontendRedirectUri;

    @Operation(summary = "카카오 로그인 시작", description = "카카오 인가 페이지로 리다이렉트합니다.")
    @GetMapping("/login")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        String authUrl = authorizeUri
            + "?client_id=" + clientId
            + "&redirect_uri=" + redirectUri
            + "&response_type=code";
        response.sendRedirect(authUrl);
    }

    @Operation(summary = "카카오 콜백", description = "인가코드로 자체 AT/RT를 발급하고 프론트로 리다이렉트합니다.")
    @GetMapping("/callback")
    public void kakaoCallback(@RequestParam String code,
                              HttpServletResponse response) throws IOException {
        TokenResponse tokenResponse = authService.kakaoCallback(code, response);
        response.sendRedirect(frontendRedirectUri + "?accessToken=" + tokenResponse.accessToken());
    }
}

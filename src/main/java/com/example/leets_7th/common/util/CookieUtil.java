package com.example.leets_7th.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {

    private static final String ACCESS_TOKEN_COOKIE  = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    @Value("${jwt.access-token-expire-ms}")
    private long accessTokenExpireMs;

    @Value("${jwt.refresh-token-expire-ms}")
    private long refreshTokenExpireMs;

    public void addAccessTokenCookie(HttpServletResponse response, String token) {
        addCookie(response, ACCESS_TOKEN_COOKIE, token, (int) (accessTokenExpireMs / 1000));
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
        addCookie(response, REFRESH_TOKEN_COOKIE, token, (int) (refreshTokenExpireMs / 1000));
    }

    // 쿠키에서 값 읽기
    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookieValue(request, ACCESS_TOKEN_COOKIE);
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookieValue(request, REFRESH_TOKEN_COOKIE);
    }


    // Access / Refresh Token 쿠키 삭제 (로그아웃)
    public void deleteAccessTokenCookie(HttpServletResponse response) {
        deleteCookie(response, ACCESS_TOKEN_COOKIE);
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        deleteCookie(response, REFRESH_TOKEN_COOKIE);
    }


    // 내부 공통 메서드
    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);   // XSS 방어
        cookie.setSecure(true);     // HTTPS 에서만 전송
        cookie.setPath("/");        // 모든 경로에서 전송
        cookie.setMaxAge(maxAgeSeconds);

        // SameSite=Strict 설정 (CSRF 방어) — Cookie API가 SameSite 미지원이므로 헤더 직접 추가
        String cookieHeader = String.format(
                "%s=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Strict",
                name, value, maxAgeSeconds
        );
        response.addHeader("Set-Cookie", cookieHeader);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        String cookieHeader = String.format(
                "%s=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict",
                name
        );
        response.addHeader("Set-Cookie", cookieHeader);
    }

    private Optional<String> getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();

        return Arrays.stream(request.getCookies())
                .filter(cookie -> name.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}

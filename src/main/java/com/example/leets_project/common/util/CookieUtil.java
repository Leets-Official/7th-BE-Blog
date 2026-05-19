package com.example.leets_project.common.util;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CookieUtil {

    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    // 로컬: false, 운영: true - application.yml로 분기
    @Value("${cookie.secure:false}")
    private boolean secure;

    // RefreshToken HttpOnly 쿠키 설정
    public void addRefreshTokenCookie(HttpServletResponse response,
                                      String token,
                                      long expirationMillis) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(secure)
                .path("/")
                .maxAge(expirationMillis / 1000)
                .sameSite("Lax") // Strict → Lax: OAuth2 redirect 흐름 대응
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 요청에서 RefreshToken 쿠키 추출
    public String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new GeneralException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(jakarta.servlet.http.Cookie::getValue)
                .orElseThrow(() -> new GeneralException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    // RefreshToken 쿠키 삭제 (로그아웃)
    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(secure)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
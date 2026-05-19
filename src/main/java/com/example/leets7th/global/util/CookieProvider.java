package com.example.leets7th.global.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieProvider {

    public void addCookie(HttpServletResponse response, String name, String value, Duration maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        addCookie(response, name, "", Duration.ZERO);
    }
}

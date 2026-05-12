package com.leets.blog.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

public class CookieUtils {

    public static ResponseCookie createCookie(String name, String value, long maxAgeSeconds, JwtProperties props) {
        return ResponseCookie.from(name, value)
                .httpOnly(props.isHttpOnly())
                .secure(props.isSecure())
                .path("/")
                .maxAge(maxAgeSeconds)
                .sameSite(props.getSameSite())
                .build();
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }
}

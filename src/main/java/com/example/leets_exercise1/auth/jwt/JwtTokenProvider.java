package com.example.leets_exercise1.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final long ACCESS_TOKEN_VALID_MILLISECONDS = 1000L * 60 * 30;
    private static final long REFRESH_TOKEN_VALID_MILLISECONDS = 1000L * 60 * 60 * 24 * 14;

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${jwt.secret:leets-exercise-default-jwt-secret-key-must-be-long}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String email) {
        return createToken(userId, email, ACCESS_TOKEN_VALID_MILLISECONDS);
    }

    public String createRefreshToken(Long userId, String email) {
        return createToken(userId, email, REFRESH_TOKEN_VALID_MILLISECONDS);
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public Long getUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return LocalDateTime.now().plusSeconds(REFRESH_TOKEN_VALID_MILLISECONDS / 1000);
    }

    private String createToken(Long userId, String email, long validMilliseconds) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validMilliseconds);

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiresAt)
                .signWith(secretKey)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

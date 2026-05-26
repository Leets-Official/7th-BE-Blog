package com.example.demo.global.security;

import com.example.demo.global.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-minutes}")
    private long accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-days}")
    private long refreshTokenExpirationDays;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Authentication authentication) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        return createToken(principal.getId(), principal.getEmail(), accessTokenExpirationMinutes, ChronoUnit.MINUTES);
    }

    public String createAccessToken(CustomUserPrincipal principal) {
        return createToken(principal.getId(), principal.getEmail(), accessTokenExpirationMinutes, ChronoUnit.MINUTES);
    }

    public String createRefreshToken(CustomUserPrincipal principal) {
        return createToken(principal.getId(), principal.getEmail(), refreshTokenExpirationDays, ChronoUnit.DAYS);
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "만료된 토큰입니다.");
        } catch (SecurityException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN_SIGNATURE", "토큰 서명이 올바르지 않습니다.");
        } catch (MalformedJwtException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "MALFORMED_TOKEN", "토큰 형식이 올바르지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_TOKEN", "지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "EMPTY_TOKEN", "토큰이 비어 있습니다.");
        }
    }

    public Long getUserId(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userId", Long.class);
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createToken(Long userId, String email, long amount, ChronoUnit unit) {
        Instant now = Instant.now();
        Instant expiry = now.plus(amount, unit);

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(secretKey)
                .compact();
    }
}

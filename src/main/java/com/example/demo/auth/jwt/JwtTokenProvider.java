package com.example.demo.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secret = "this-is-demo-secret-key-for-jwt-token-this-is-demo-secret-key";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    private final long accessTokenExpireTime = 1000L * 60 * 30;
    private final long refreshTokenExpireTime = 1000L * 60 * 60 * 24 * 7;

    public String createAccessToken(Long userId, String email) {
        return createToken(userId, email, accessTokenExpireTime);
    }

    public String createRefreshToken(Long userId, String email) {
        return createToken(userId, email, refreshTokenExpireTime);
    }

    private String createToken(Long userId, String email, long expireTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
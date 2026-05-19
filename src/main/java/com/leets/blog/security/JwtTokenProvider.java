package com.leets.blog.security;

import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private Key key;
    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_NICKNAME = "nickname";
    private static final String CLAIM_ROLE = "role";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String email, String nickname, String role) {
        return createToken(userId, email, nickname, role, jwtProperties.getAccessTokenExpirationSeconds(), ACCESS);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, null, null, null, jwtProperties.getRefreshTokenExpirationSeconds(), REFRESH);
    }

    private String createToken(Long userId, String email, String nickname, String role, long expirationSeconds, String type) {
        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationSeconds * 1000))
                .claim(CLAIM_TYPE, type);

        if (email != null) {
            builder.claim(CLAIM_EMAIL, email);
        }

        if (nickname != null) {
            builder.claim(CLAIM_NICKNAME, nickname);
        }

        if (role != null) {
            builder.claim(CLAIM_ROLE, role);
        }

        return builder.signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        return REFRESH.equals(getClaims(token).get(CLAIM_TYPE, String.class));
    }

    public boolean isAccessToken(String token) {
        return ACCESS.equals(getClaims(token).get(CLAIM_TYPE, String.class));
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (!ACCESS.equals(claims.get(CLAIM_TYPE, String.class))) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        Long userId = Long.parseLong(claims.getSubject());
        String email = claims.get(CLAIM_EMAIL, String.class);
        String nickname = claims.get(CLAIM_NICKNAME, String.class);
        UserRole role = UserRole.valueOf(claims.get(CLAIM_ROLE, String.class));

        AuthUser authUser = new AuthUser(userId, email, nickname, role);
        return new UsernamePasswordAuthenticationToken(
                authUser,
                token,
                List.of(new SimpleGrantedAuthority(role.name()))
        );
    }
}

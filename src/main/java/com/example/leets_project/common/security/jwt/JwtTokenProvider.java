package com.example.leets_project.common.security.jwt;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String ROLE_KEY = "role";
    private static final String EMAIL_KEY = "email";
    private static final String TOKEN_TYPE_KEY = "tokenType";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    // Access Token 생성
    public String generateAccessToken(Long userId, String email, String role) {
        return buildToken(
                userId,
                email,
                role,
                TokenType.ACCESS,
                jwtProperties.getAccessTokenExpirationMillis()
        );
    }
    // Refresh Token 생성
    public String generateRefreshToken(Long userId) {
        return buildToken(
                userId,
                null,
                null,
                TokenType.REFRESH,
                jwtProperties.getRefreshTokenExpirationMillis()
        );
    }
    // 공통 토큰 빌더: Access/Refresh 토큰의 공통 구조 정의
    private String buildToken(Long userId, String email, String role, TokenType type, long expirationMillis) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(TOKEN_TYPE_KEY, type.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMillis))
                .signWith(secretKey);

        // Access Token일 경우에만 추가 정보(email, role) 삽입
        if (email != null) builder.claim(EMAIL_KEY, email);
        if (role != null) builder.claim(ROLE_KEY, role);

        return builder.compact();
    }

    // JWT 토큰을 Spring Security의 Authentication 객체로 변환
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String role = claims.get(ROLE_KEY, String.class);
        Long userId = Long.parseLong(claims.getSubject());

        // Spring Security 권한 형식 : "ROLE_USER" 형태로 변환
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // Principal에 userId를 담아 컨트롤러에서 @AuthenticationPrincipal로 꺼내 쓰기 용이하게 함
        return new UsernamePasswordAuthenticationToken(userId, token, authorities);
    }

    // 토큰 유효성 검증 (서명 불일치, 만료 등 확인)
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 비어있거나 잘못되었습니다.");
        }
        return false;
    }

    // Token Type 일치 검증
    public void validateTokenType(Claims claims, TokenType expectedType) {
        String tokenType = claims.get(TOKEN_TYPE_KEY, String.class);
        if (tokenType == null || !tokenType.equals(expectedType.name())) {
            throw new GeneralException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Long getUserId(String token) {

        return Long.parseLong(
                parseClaims(token).getSubject()
        );
    }
    public String getEmail(String token) {

        return parseClaims(token)
                .get(EMAIL_KEY, String.class);
    }
    public String getRole(String token) {

        return parseClaims(token)
                .get(ROLE_KEY, String.class);
    }

    // 토큰 내부의 Claims 추출
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    // Authorization Header 에서 Bearer Token 추출
    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if ( StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}

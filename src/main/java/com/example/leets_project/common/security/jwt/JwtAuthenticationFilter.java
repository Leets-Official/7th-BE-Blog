package com.example.leets_project.common.security.jwt;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    // HTTP 요청 한 번당 한 번씩 실행
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 이전 인증 정보 제거
        SecurityContextHolder.clearContext();

        try {
            // Authorization Header 에서 JWT 추출
            String token = jwtTokenProvider.resolveToken(request);
            // 토큰 존재, 유효한 경우 인증 정보 설정
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

                Claims claims = jwtTokenProvider.parseClaims(token);
                // Token Type 검증
                jwtTokenProvider.validateTokenType(claims, TokenType.ACCESS);

                // Authentication 객체 생성 및 SecurityContext 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("인증 저장 완료 userId={}, uri={}", authentication.getName(), request.getRequestURI());
                }
            } catch (Exception e) {
                // 인증 실패 시 인증 정보 제거 및 로그 반환
                SecurityContextHolder.clearContext();
                log.warn("JWT 인증 실패: {}", e.getMessage());
            }
        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }
}

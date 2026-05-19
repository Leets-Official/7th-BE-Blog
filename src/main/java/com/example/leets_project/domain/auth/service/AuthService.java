package com.example.leets_project.domain.auth.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.common.security.jwt.JwtProperties;
import com.example.leets_project.common.security.jwt.JwtTokenProvider;
import com.example.leets_project.common.security.jwt.TokenType;
import com.example.leets_project.domain.auth.entity.RefreshToken;
import com.example.leets_project.domain.auth.repository.RefreshTokenRepository;
import com.example.leets_project.domain.auth.web.dto.LoginRequest;
import com.example.leets_project.domain.auth.web.dto.LoginResponse;
import com.example.leets_project.domain.auth.web.dto.SignUpRequest;
import com.example.leets_project.domain.auth.web.dto.TokenResponse;
import com.example.leets_project.domain.user.entity.User;
import com.example.leets_project.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void signUp(SignUpRequest request) {
        validateDuplicateEmail(request.email());
        validateDuplicateNickname(request.nickname());

        userRepository.save(User.builder()
                .name(request.nickname())
                .nickname(request.nickname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build());

        log.info("회원가입 완료: email={}", request.email());
    }
    // 로그인
    @Transactional
    public LoginResult login(LoginRequest request) {
        User user = findUserByEmail(request.email());
        validatePassword(request.password(), user);

        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        saveOrRotateRefreshToken(user.getId(), refreshToken);

        log.info("로그인 성공: userId={}", user.getId());

        return new LoginResult(
                new LoginResponse(
                        new TokenResponse(accessToken),
                        new LoginResponse.UserInfoResponse(
                                user.getId(), user.getEmail(), user.getNickname())
                ),
                refreshToken
        );
    }
    // 토큰 재발급
    @Transactional
    public ReissueResult reissue(String refreshToken) {
        Claims claims = parseClaims(refreshToken);
        Long userId = Long.parseLong(claims.getSubject());

        jwtTokenProvider.validateTokenType(claims, TokenType.REFRESH);

        RefreshToken storedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        validateStoredToken(storedToken, refreshToken, userId);

        User user = findUserById(userId);
        String newAccessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

        storedToken.rotate(newRefreshToken, calculateExpiryDate());

        log.info("토큰 재발급 성공: userId={}", userId);

        return new ReissueResult(new TokenResponse(newAccessToken), newRefreshToken);
    }
    // 로그아웃
    @Transactional
    public void logout(String refreshToken) {
        try {
            Claims claims = jwtTokenProvider.parseClaims(refreshToken);
            refreshTokenRepository.deleteByUserId(Long.parseLong(claims.getSubject()));
            log.info("로그아웃 성공");
        } catch (ExpiredJwtException e) {
            refreshTokenRepository.deleteByUserId(
                    Long.parseLong(e.getClaims().getSubject()));
            log.info("로그아웃 성공(만료 토큰)");
        } catch (JwtException e) {
            log.warn("로그아웃 - 유효하지 않은 토큰");
        }
    }

    public record LoginResult(LoginResponse loginResponse, String refreshToken) {}
    public record ReissueResult(TokenResponse tokenResponse, String newRefreshToken) {}

    private Claims parseClaims(String token) {
        try {
            return jwtTokenProvider.parseClaims(token);
        } catch (JwtException e) {
            throw new GeneralException(ErrorCode.INVALID_TOKEN);
        }
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("로그인 실패: email={}", email);
                    return new GeneralException(ErrorCode.LOGIN_FAILED);
                });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }
    // 비밀번호 검증
    private void validatePassword(String rawPassword, User user) {
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.warn("로그인 실패: email={}", user.getEmail());
            throw new GeneralException(ErrorCode.LOGIN_FAILED);
        }
    }

    private void validateStoredToken(RefreshToken storedToken, String refreshToken, Long userId) {
        if (!storedToken.getToken().equals(refreshToken)) {
            refreshTokenRepository.delete(storedToken);
            log.warn("RefreshToken 재사용 감지: userId={}", userId);
            throw new GeneralException(ErrorCode.INVALID_TOKEN);
        }
        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            log.warn("RefreshToken 만료: userId={}", userId);
            throw new GeneralException(ErrorCode.EXPIRED_TOKEN);
        }
    }
    // Refresh token 저장/순환
    private void saveOrRotateRefreshToken(Long userId, String refreshToken) {
        LocalDateTime expiresAt = calculateExpiryDate();
        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        token -> token.rotate(refreshToken, expiresAt),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .userId(userId)
                                .token(refreshToken)
                                .expiresAt(expiresAt)
                                .build())
                );
    }
    // 만료일자 계산
    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now()
                .plusSeconds(jwtProperties.getRefreshTokenExpirationMillis() / 1000);
    }
    // 이메일 중복 검증
    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new GeneralException(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
    }
    // 닉네임 중복 검증
    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new GeneralException(ErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }
    }
}
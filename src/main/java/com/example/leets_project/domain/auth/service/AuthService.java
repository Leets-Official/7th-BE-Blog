package com.example.leets_project.domain.auth.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.common.security.jwt.JwtProperties;
import com.example.leets_project.common.security.jwt.JwtTokenProvider;
import com.example.leets_project.common.security.jwt.TokenType;
import com.example.leets_project.domain.auth.entity.RefreshToken;
import com.example.leets_project.domain.auth.oauth.kakao.dto.KakaoUserInfo;
import com.example.leets_project.domain.auth.repository.RefreshTokenRepository;
import com.example.leets_project.domain.auth.web.dto.*;
import com.example.leets_project.domain.user.entity.AuthProvider;
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

import java.time.Instant;
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
    private final KakaoOAuthService kakaoOAuthService;

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
                .authProvider(AuthProvider.LOCAL)
                .providerId(null)
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
                        TokenResponse.bearer(accessToken),
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

        storedToken.rotate(jwtTokenProvider.sha256(newRefreshToken), calculateExpiryDate());

        log.info("토큰 재발급 성공: userId={}", userId);

        return new ReissueResult(TokenResponse.bearer(newAccessToken), newRefreshToken);
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

    // 카카오 소셜 로그인/회원가입 처리
    @Transactional
    public LoginResult loginWithKakao(KakaoLoginRequest request) {
        // 인가 코드로 카카오 API 호출해 유저 정보 획득
        KakaoUserInfo kakaoUser = kakaoOAuthService.getUserInfo(request.code());

        // 이미 가입된 유저인지 검증 후 신규 가입 진행
        User user = userRepository
                .findByAuthProviderAndProviderId(AuthProvider.KAKAO, kakaoUser.providerId())
                .orElseGet(() -> createKakaoUser(kakaoUser));

        return issueLoginTokens(user);
    }

    // 카카오 신규 유저 데이터베이스 등록
    private User createKakaoUser(KakaoUserInfo kakaoUser) {
        String nickname = kakaoUser.nickname() != null && !kakaoUser.nickname().isBlank()
                ? kakaoUser.nickname()
                : "kakao_" + kakaoUser.providerId();

        return userRepository.save(User.builder()
                .email(kakaoUser.email())
                .nickname(nickname)
                .name(nickname)
                .password(null)
                .authProvider(AuthProvider.KAKAO)
                .providerId(kakaoUser.providerId())
                .build());
    }

    // 소셜/로컬 공통 로그인 토큰 발급 헬퍼
    private LoginResult issueLoginTokens(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        saveOrRotateRefreshToken(user.getId(), refreshToken);

        LoginResponse response = new LoginResponse(
                TokenResponse.bearer(accessToken),
                new LoginResponse.UserInfoResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getNickname()
                )
        );

        return new LoginResult(response, refreshToken);
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
        String refreshTokenHash = jwtTokenProvider.sha256(refreshToken);
        if (!storedToken.getTokenHash().equals(refreshTokenHash)) {
            refreshTokenRepository.delete(storedToken);
            log.warn("RefreshToken 재사용 감지: userId={}", userId);
            throw new GeneralException(ErrorCode.INVALID_TOKEN);
        }
        if (storedToken.isExpired(Instant.now())) {
            refreshTokenRepository.delete(storedToken);
            log.warn("RefreshToken 만료: userId={}", userId);
            throw new GeneralException(ErrorCode.EXPIRED_TOKEN);
        }
    }
    // Refresh token 저장/순환
    private void saveOrRotateRefreshToken(Long userId, String refreshToken) {
        String tokenHash = jwtTokenProvider.sha256(refreshToken);
        Instant expiresAt = calculateExpiryDate();
        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        token -> token.rotate(tokenHash, expiresAt),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .userId(userId)
                                .tokenHash(tokenHash)
                                .expiresAt(expiresAt)
                                .build())
                );
    }
    // 만료일자 계산
    private Instant calculateExpiryDate() {
        return Instant.now().plusMillis(jwtProperties.getRefreshTokenExpirationMillis());
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
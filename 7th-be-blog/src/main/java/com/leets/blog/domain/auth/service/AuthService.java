package com.leets.blog.domain.auth.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.common.security.JwtTokenProvider;
import com.leets.blog.domain.auth.dto.LoginRequest;
import com.leets.blog.domain.auth.dto.LoginResponse;
import com.leets.blog.domain.auth.dto.SignupRequest;
import com.leets.blog.domain.auth.dto.SignupResponse;
import com.leets.blog.domain.auth.dto.TokenReissueRequest;
import com.leets.blog.domain.auth.dto.TokenReissueResponse;
import com.leets.blog.domain.auth.entity.RefreshToken;
import com.leets.blog.domain.auth.repository.RefreshTokenRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        validateSignupRequest(request);

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .build();

        return SignupResponse.from(userRepository.save(user));
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .filter(foundUser -> !foundUser.isDeleted())
                .orElseThrow(() -> new GeneralException(BaseErrorCode.INVALID_LOGIN));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new GeneralException(BaseErrorCode.INVALID_LOGIN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);
        saveRefreshToken(user, refreshToken);

        return new LoginResponse(accessToken, refreshToken);
    }

    public TokenReissueResponse reissueAccessToken(TokenReissueRequest request) {
        String requestRefreshToken = request.refreshToken();
        if (!jwtTokenProvider.validateToken(requestRefreshToken) || !jwtTokenProvider.isRefreshToken(requestRefreshToken)) {
            throw new GeneralException(BaseErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestRefreshToken)
                .orElseThrow(() -> new GeneralException(BaseErrorCode.INVALID_REFRESH_TOKEN));

        if (refreshToken.isExpired(LocalDateTime.now())) {
            throw new GeneralException(BaseErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = refreshToken.getUser();
        if (user.isDeleted()) {
            throw new GeneralException(BaseErrorCode.INVALID_REFRESH_TOKEN);
        }

        return new TokenReissueResponse(jwtTokenProvider.createAccessToken(user));
    }

    private void validateSignupRequest(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_EMAIL);
        }
        if (userRepository.existsByName(request.name())) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_NICKNAME);
        }
    }

    private void saveRefreshToken(User user, String refreshToken) {
        LocalDateTime expiresAt = LocalDateTime.ofInstant(
                jwtTokenProvider.getExpiration(refreshToken).toInstant(),
                ZoneId.systemDefault()
        );

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresentOrElse(
                        savedToken -> savedToken.update(refreshToken, expiresAt),
                        () -> refreshTokenRepository.save(RefreshToken.create(user, refreshToken, expiresAt))
                );
    }
}

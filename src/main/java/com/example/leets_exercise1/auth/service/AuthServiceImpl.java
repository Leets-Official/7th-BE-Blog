package com.example.leets_exercise1.auth.service;

import com.example.leets_exercise1.auth.dto.request.LoginRequest;
import com.example.leets_exercise1.auth.dto.request.SignUpRequest;
import com.example.leets_exercise1.auth.dto.request.TokenReissueRequest;
import com.example.leets_exercise1.auth.dto.response.LoginResponse;
import com.example.leets_exercise1.auth.dto.response.SignUpResponse;
import com.example.leets_exercise1.auth.dto.response.TokenReissueResponse;
import com.example.leets_exercise1.auth.exception.EmailAlreadyExistsException;
import com.example.leets_exercise1.auth.exception.InvalidLoginException;
import com.example.leets_exercise1.auth.exception.NicknameAlreadyExistsException;
import com.example.leets_exercise1.auth.exception.RefreshTokenNotFoundException;
import com.example.leets_exercise1.auth.jwt.JwtTokenProvider;
import com.example.leets_exercise1.domain.auth.RefreshToken;
import com.example.leets_exercise1.domain.user.AuthProvider;
import com.example.leets_exercise1.domain.user.Role;
import com.example.leets_exercise1.domain.user.User;
import com.example.leets_exercise1.repository.RefreshTokenRepository;
import com.example.leets_exercise1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new NicknameAlreadyExistsException();
        }

        User user = User.builder()
                .age(request.getAge())
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .provider(AuthProvider.LOCAL)
                .build();

        return SignUpResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getEmail());

        refreshTokenRepository.findByUser(user)
                .ifPresentOrElse(
                        savedToken -> savedToken.update(refreshToken, jwtTokenProvider.getRefreshTokenExpiresAt()),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .user(user)
                                .refreshToken(refreshToken)
                                .expiresAt(jwtTokenProvider.getRefreshTokenExpiresAt())
                                .build())
                );

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    @Override
    public TokenReissueResponse reissue(TokenReissueRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new RefreshTokenNotFoundException();
        }

        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);
        User user = savedToken.getUser();

        return TokenReissueResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getEmail()))
                .build();
    }
}

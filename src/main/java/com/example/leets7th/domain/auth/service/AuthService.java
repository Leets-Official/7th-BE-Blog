package com.example.leets7th.domain.auth.service;

import com.example.leets7th.domain.auth.dto.LoginRequest;
import com.example.leets7th.domain.auth.dto.SignUpRequest;
import com.example.leets7th.domain.auth.dto.TokenResponse;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.exception.DuplicateEmailException;
import com.example.leets7th.global.exception.DuplicateNicknameException;
import com.example.leets7th.global.exception.InvalidCredentialsException;
import com.example.leets7th.global.exception.InvalidTokenException;
import com.example.leets7th.global.exception.UserNotFoundException;
import com.example.leets7th.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmailOrNickname(request.email(), request.nickname())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new DuplicateEmailException();
            }
            throw new DuplicateNicknameException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        userRepository.save(User.create(request.email(), encodedPassword, request.nickname()));
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public String refresh(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        Long userId = jwtProvider.getUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidTokenException();
        }

        return jwtProvider.generateAccessToken(user.getId(), user.getRole());
    }
}

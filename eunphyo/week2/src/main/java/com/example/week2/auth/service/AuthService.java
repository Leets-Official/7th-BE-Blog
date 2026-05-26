package com.example.week2.auth.service;

import com.example.week2.auth.dto.AuthRequest;
import com.example.week2.auth.dto.AuthResponse;
import com.example.week2.global.security.JwtProvider;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
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
    public void signup(AuthRequest.SignupRequest request) {

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .name(request.getName())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER)
                .provider(User.AuthProvider.LOCAL)
                .build();

        userRepository.save(user);
    }

    public AuthResponse.TokenResult login(AuthRequest.LoginRequest request) {

        User user = userRepository.findByEmailAndProvider(request.getEmail(), User.AuthProvider.LOCAL)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        return new AuthResponse.TokenResult(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse.TokenResult reissue(String refreshToken) {

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtProvider.getUserId(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken =
                jwtProvider.createAccessToken(user.getId(), user.getRole());

        String newRefreshToken =
                jwtProvider.createRefreshToken(user.getId());

        return new AuthResponse.TokenResult(newAccessToken, newRefreshToken);
    }
}

// AuthService.java
package com.leets.blog.auth.service;

import com.leets.blog.auth.dto.*;
import com.leets.blog.auth.jwt.JwtProvider;
import com.leets.blog.entity.User;
import com.leets.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KakaoService kakaoService;

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) throw new RuntimeException("이메일 중복");
        if (userRepository.existsByNickname(request.nickname())) throw new RuntimeException("닉네임 중복");

        userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build());
    }

    public TokenResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("미가입자"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) throw new RuntimeException("비번틀림");

        return TokenResponse.of(jwtProvider.createAccessToken(user.getEmail()), jwtProvider.createRefreshToken(user.getEmail()));
    }

    // 카카오 로그인
    @Transactional
    public TokenResponse kakaoLogin(String code) {
        String kakaoAccessToken = kakaoService.getAccessToken(code);
        KakaoUserResponse userInfo = kakaoService.getUserInfo(kakaoAccessToken);

        String kakaoId = userInfo.id().toString();
        String nickname = userInfo.properties().nickname();

        User user = userRepository.findByEmail(kakaoId)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(kakaoId)
                        .nickname(nickname)
                        .name(nickname)
                        .password(passwordEncoder.encode(kakaoId))
                        .build()));

        return TokenResponse.of(
                jwtProvider.createAccessToken(user.getEmail()),
                jwtProvider.createRefreshToken(user.getEmail())
        );
    }
}
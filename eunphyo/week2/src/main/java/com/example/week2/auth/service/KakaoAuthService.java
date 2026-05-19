package com.example.week2.auth.service;

import com.example.week2.auth.client.KakaoClient;
import com.example.week2.auth.dto.AuthResponse;
import com.example.week2.auth.dto.KakaoResponse;
import com.example.week2.global.security.JwtProvider;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AuthResponse.TokenResult kakaologin(String code) {

        String kakaoAccessToken = kakaoClient.getAccessToken(code);

        KakaoResponse.KakaoUserInfo userInfo =
                kakaoClient.getUserInfo(kakaoAccessToken);

        User user = userRepository.findByEmail(userInfo.email())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(userInfo.email())
                            .nickname(userInfo.nickname())
                            .password(null)
                            .build();

                    return userRepository.save(newUser);
                });

        String accessToken =
                jwtProvider.createAccessToken(user.getId(), user.getRole());

        String refreshToken =
                jwtProvider.createRefreshToken(user.getId());

        return new AuthResponse.TokenResult(
                accessToken,
                refreshToken
        );
    }
}
package com.example.week2.auth.service;

import com.example.week2.auth.client.KakaoClient;
import com.example.week2.auth.dto.AuthResponse;
import com.example.week2.auth.dto.KakaoResponse;
import com.example.week2.global.security.JwtProvider;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String getAuthorizationUrl() {
        return kakaoClient.getAuthorizationUrl();
    }

    @Transactional
    public AuthResponse.TokenResult kakaoLogin(String code) {

        String kakaoAccessToken = kakaoClient.getAccessToken(code);

        KakaoResponse.KakaoUserInfo userInfo =
                kakaoClient.getUserInfo(kakaoAccessToken);

        User user = userRepository.findByProviderAndProviderId(
                        User.AuthProvider.KAKAO,
                        userInfo.providerId()
                )
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .name(userInfo.nickname())
                            .nickname(createKakaoNickname(userInfo.providerId()))
                            .password(passwordEncoder.encode("KAKAO_USER_" + userInfo.providerId()))
                            .role(User.Role.USER)
                            .provider(User.AuthProvider.KAKAO)
                            .providerId(userInfo.providerId())
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

    private String createKakaoNickname(String providerId) {
        String baseNickname = "kakao_" + providerId;
        String nickname = baseNickname;
        int suffix = 1;

        while (userRepository.existsByNickname(nickname)) {
            nickname = baseNickname + "_" + suffix;
            suffix++;
        }

        return nickname;
    }
}

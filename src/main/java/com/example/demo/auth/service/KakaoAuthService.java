package com.example.demo.auth.service;

import com.example.demo.auth.dto.KakaoLoginRequest;
import com.example.demo.auth.dto.TokenResponse;
import com.example.demo.auth.jwt.JwtTokenProvider;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoAuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final RestClient restClient = RestClient.create();

    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    public TokenResponse kakaoLogin(KakaoLoginRequest request) {
        String kakaoAccessToken = getKakaoAccessToken(request.getCode());

        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        User user = userRepository.findByKakaoId(kakaoUserInfo.kakaoId())
                .orElseGet(() -> createKakaoUser(kakaoUserInfo));

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), user.getEmail());

        user.updateRefreshToken(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String getKakaoAccessToken(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        Map response = restClient.post()
                .uri(kakaoTokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("access_token") == null) {
            throw new IllegalArgumentException("카카오 access token 발급에 실패했습니다.");
        }

        return response.get("access_token").toString();
    }

    private KakaoUserInfo getKakaoUserInfo(String kakaoAccessToken) {

        Map response = restClient.get()
                .uri(kakaoUserInfoUri)
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .retrieve()
                .body(Map.class);

        if (response == null || response.get("id") == null) {
            throw new IllegalArgumentException("카카오 사용자 정보 조회에 실패했습니다.");
        }

        Long kakaoId = Long.valueOf(response.get("id").toString());

        Map kakaoAccount = (Map) response.get("kakao_account");
        Map profile = kakaoAccount != null ? (Map) kakaoAccount.get("profile") : null;

        String email = kakaoAccount != null && kakaoAccount.get("email") != null
                ? kakaoAccount.get("email").toString()
                : "kakao_" + kakaoId + "@kakao.local";

        String nickname = profile != null && profile.get("nickname") != null
                ? profile.get("nickname").toString()
                : "kakao_user_" + kakaoId;

        return new KakaoUserInfo(kakaoId, email, nickname);
    }

    private User createKakaoUser(KakaoUserInfo kakaoUserInfo) {

        String nickname = kakaoUserInfo.nickname();

        if (userRepository.existsByNickname(nickname)) {
            nickname = nickname + "_" + kakaoUserInfo.kakaoId();
        }

        User user = User.builder()
                .name(nickname)
                .email(kakaoUserInfo.email())
                .nickname(nickname)
                .password(passwordEncoder.encode("KAKAO_USER"))
                .kakaoId(kakaoUserInfo.kakaoId())
                .build();

        return userRepository.save(user);
    }

    private record KakaoUserInfo(
            Long kakaoId,
            String email,
            String nickname
    ) {
    }
}
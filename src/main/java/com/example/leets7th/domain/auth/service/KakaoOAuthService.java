package com.example.leets7th.domain.auth.service;

import com.example.leets7th.domain.auth.dto.KakaoTokenResponse;
import com.example.leets7th.domain.auth.dto.KakaoUserInfoResponse;
import com.example.leets7th.domain.auth.dto.TokenResponse;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.exception.KakaoAuthException;
import com.example.leets7th.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoOAuthService {

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RestClient restClient;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String getKakaoLoginUrl() {
        return "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
    }

    @Transactional
    public TokenResponse kakaoLogin(String code) {
        KakaoTokenResponse kakaoToken = fetchKakaoToken(code);
        KakaoUserInfoResponse userInfo = fetchKakaoUserInfo(kakaoToken.accessToken());

        User user = userRepository.findByKakaoId(userInfo.id())
                .orElseGet(() -> registerKakaoUser(userInfo));

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private KakaoTokenResponse fetchKakaoToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        try {
            return restClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(KakaoTokenResponse.class);
        } catch (RestClientException e) {
            throw new KakaoAuthException();
        }
    }

    private KakaoUserInfoResponse fetchKakaoUserInfo(String kakaoAccessToken) {
        try {
            return restClient.get()
                    .uri(KAKAO_USER_INFO_URL)
                    .header("Authorization", "Bearer " + kakaoAccessToken)
                    .retrieve()
                    .body(KakaoUserInfoResponse.class);
        } catch (RestClientException e) {
            throw new KakaoAuthException();
        }
    }

    private User registerKakaoUser(KakaoUserInfoResponse userInfo) {
        String nickname = resolveUniqueNickname(userInfo.nickname(), userInfo.id());
        String tempPassword = passwordEncoder.encode(UUID.randomUUID().toString());
        User user = User.createByKakao(userInfo.id(), userInfo.email(), nickname, tempPassword);
        return userRepository.save(user);
    }

    private String resolveUniqueNickname(String base, Long kakaoId) {
        if (!userRepository.existsByNickname(base)) {
            return base;
        }
        return base + "_" + kakaoId;
    }
}

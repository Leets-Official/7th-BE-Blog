package com.example.demo.domain.auth.service;

import com.example.demo.domain.auth.dto.KakaoLoginResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.security.CustomUserPrincipal;
import com.example.demo.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoOAuthService {

    private static final String KAKAO_AUTHORIZE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final RestClient restClient;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret:}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public String generateState() {
        byte[] randomBytes = new byte[24];
        SECURE_RANDOM.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public String buildAuthorizationUri(String state) {
        validateKakaoOAuthConfig();

        return UriComponentsBuilder.fromHttpUrl(KAKAO_AUTHORIZE_URL)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "account_email,profile_nickname")
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    public void validateState(String state, String savedState) {
        if (state == null || state.isBlank() || savedState == null || savedState.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_KAKAO_STATE", "유효하지 않은 카카오 OAuth state 값입니다.");
        }

        if (!state.equals(savedState)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_KAKAO_STATE", "카카오 OAuth state 검증에 실패했습니다.");
        }
    }

    @Transactional
    public KakaoLoginResponse login(String code) {
        validateKakaoOAuthConfig();

        KakaoTokenResponse tokenResponse = requestToken(code);
        KakaoUserResponse kakaoUser = requestUserInfo(tokenResponse.accessToken());

        Long kakaoId = kakaoUser.id();
        String email = kakaoUser.kakaoAccount() == null ? null : kakaoUser.kakaoAccount().email();
        String nickname = extractNickname(kakaoUser);

        if (email == null || email.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_EMAIL_REQUIRED", "카카오 계정 이메일 동의가 필요합니다.");
        }
        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_NICKNAME_REQUIRED", "카카오 프로필 닉네임 동의가 필요합니다.");
        }

        UserRegistrationResult registrationResult = userRepository.findByKakaoId(kakaoId)
                .map(user -> new UserRegistrationResult(user, false))
                .orElseGet(() -> registerOrLink(kakaoId, email, nickname));

        User user = registrationResult.user();

        String accessToken = jwtTokenProvider.createAccessToken(CustomUserPrincipal.from(user));
        String refreshToken = jwtTokenProvider.createRefreshToken(CustomUserPrincipal.from(user));
        user.updateRefreshToken(refreshToken);

        return new KakaoLoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getNickname(),
                registrationResult.newlyRegistered()
        );
    }

    private UserRegistrationResult registerOrLink(Long kakaoId, String email, String nickname) {
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.linkKakao(kakaoId);
                    return new UserRegistrationResult(existingUser, false);
                })
                .orElseGet(() -> {
                    if (userRepository.existsByName(nickname)) {
                        throw new CustomException(HttpStatus.CONFLICT, "NICKNAME_ALREADY_EXISTS", "이미 등록된 닉네임입니다.");
                    }
                    return new UserRegistrationResult(
                            userRepository.save(User.registerKakao(kakaoId, email, nickname)),
                            true
                    );
                });
    }

    private KakaoTokenResponse requestToken(String code) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("redirect_uri", redirectUri);
        form.add("code", code);
        if (clientSecret != null && !clientSecret.isBlank()) {
            form.add("client_secret", clientSecret);
        }

        try {
            return restClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(form)
                    .retrieve()
                    .body(KakaoTokenResponse.class);
        } catch (RestClientResponseException e) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "KAKAO_TOKEN_REQUEST_FAILED",
                    "카카오 토큰 발급에 실패했습니다. 응답: " + e.getResponseBodyAsString()
            );
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_TOKEN_REQUEST_FAILED", "카카오 토큰 발급에 실패했습니다.");
        }
    }

    private KakaoUserResponse requestUserInfo(String kakaoAccessToken) {
        try {
            return restClient.get()
                    .uri(KAKAO_USER_INFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                    .retrieve()
                    .body(KakaoUserResponse.class);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "KAKAO_USER_INFO_FAILED", "카카오 사용자 정보 조회에 실패했습니다.");
        }
    }

    private String extractNickname(KakaoUserResponse kakaoUser) {
        if (kakaoUser.kakaoAccount() != null
                && kakaoUser.kakaoAccount().profile() != null
                && kakaoUser.kakaoAccount().profile().nickname() != null) {
            return kakaoUser.kakaoAccount().profile().nickname();
        }
        return null;
    }

    private void validateKakaoOAuthConfig() {
        if (clientId == null || clientId.isBlank()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "KAKAO_CLIENT_ID_MISSING", "카카오 REST API 키가 설정되지 않았습니다.");
        }
        if (redirectUri == null || redirectUri.isBlank()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "KAKAO_REDIRECT_URI_MISSING", "카카오 Redirect URI가 설정되지 않았습니다.");
        }
    }

    private record KakaoTokenResponse(
            @JsonProperty("token_type")
            String tokenType,
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("expires_in")
            Integer expiresIn,
            @JsonProperty("refresh_token")
            String refreshToken,
            @JsonProperty("refresh_token_expires_in")
            Integer refreshTokenExpiresIn,
            String scope
    ) {
    }

    private record KakaoUserResponse(
            Long id,
            @JsonProperty("kakao_account")
            KakaoAccount kakaoAccount
    ) {
    }

    private record KakaoAccount(
            String email,
            KakaoProfile profile
    ) {
    }

    private record KakaoProfile(
            String nickname
    ) {
    }

    private record UserRegistrationResult(
            User user,
            boolean newlyRegistered
    ) {
    }
}

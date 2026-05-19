package com.example.leets_exercise1.auth.service;

import com.example.leets_exercise1.auth.dto.kakao.KakaoTokenResponse;
import com.example.leets_exercise1.auth.dto.kakao.KakaoUserInfoResponse;
import com.example.leets_exercise1.auth.dto.request.KakaoLoginRequest;
import com.example.leets_exercise1.auth.dto.request.LoginRequest;
import com.example.leets_exercise1.auth.dto.request.SignUpRequest;
import com.example.leets_exercise1.auth.dto.request.TokenReissueRequest;
import com.example.leets_exercise1.auth.dto.response.KakaoLoginUrlResponse;
import com.example.leets_exercise1.auth.dto.response.LoginResponse;
import com.example.leets_exercise1.auth.dto.response.SignUpResponse;
import com.example.leets_exercise1.auth.dto.response.TokenReissueResponse;
import com.example.leets_exercise1.auth.exception.EmailAlreadyExistsException;
import com.example.leets_exercise1.auth.exception.InvalidLoginException;
import com.example.leets_exercise1.auth.exception.KakaoLoginFailedException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private static final String KAKAO_AUTHORIZE_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestClient restClient;

    @Value("${kakao.client-id:}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri:}")
    private String kakaoRedirectUri;

    @Value("${kakao.client-secret:}")
    private String kakaoClientSecret;

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

        return issueTokenResponse(user);
    }

    @Override
    public KakaoLoginUrlResponse getKakaoLoginUrl() {
        validateKakaoProperties();

        String loginUrl = UriComponentsBuilder.fromUriString(KAKAO_AUTHORIZE_URL)
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();

        return KakaoLoginUrlResponse.builder()
                .loginUrl(loginUrl)
                .build();
    }

    @Override
    @Transactional
    public LoginResponse kakaoLogin(KakaoLoginRequest request) {
        validateKakaoProperties();

        KakaoTokenResponse tokenResponse = requestKakaoToken(request.getCode());
        if (tokenResponse == null || !StringUtils.hasText(tokenResponse.getAccessToken())) {
            throw new KakaoLoginFailedException();
        }
        KakaoUserInfoResponse userInfo = requestKakaoUserInfo(tokenResponse.getAccessToken());
        User user = findOrCreateKakaoUser(userInfo);

        return issueTokenResponse(user);
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

    private KakaoTokenResponse requestKakaoToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);
        if (StringUtils.hasText(kakaoClientSecret)) {
            body.add("client_secret", kakaoClientSecret);
        }

        try {
            return restClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .body(KakaoTokenResponse.class);
        } catch (RestClientException e) {
            throw new KakaoLoginFailedException();
        }
    }

    private KakaoUserInfoResponse requestKakaoUserInfo(String kakaoAccessToken) {
        if (!StringUtils.hasText(kakaoAccessToken)) {
            throw new KakaoLoginFailedException();
        }

        try {
            return restClient.get()
                    .uri(KAKAO_USER_INFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                    .retrieve()
                    .body(KakaoUserInfoResponse.class);
        } catch (RestClientException e) {
            throw new KakaoLoginFailedException();
        }
    }

    private User findOrCreateKakaoUser(KakaoUserInfoResponse userInfo) {
        if (userInfo == null || userInfo.getId() == null) {
            throw new KakaoLoginFailedException();
        }

        String providerId = String.valueOf(userInfo.getId());
        return userRepository.findByProviderAndProviderId(AuthProvider.KAKAO, providerId)
                .or(() -> userRepository.findByEmail(userInfo.getEmail()))
                .orElseGet(() -> userRepository.save(User.builder()
                        .age(0)
                        .name(userInfo.getNickname())
                        .email(userInfo.getEmail())
                        .nickname(createUniqueNickname(userInfo.getNickname(), providerId))
                        .password("")
                        .role(Role.USER)
                        .provider(AuthProvider.KAKAO)
                        .providerId(providerId)
                        .build()));
    }

    private String createUniqueNickname(String nickname, String providerId) {
        String baseNickname = StringUtils.hasText(nickname) ? nickname : "kakao_" + providerId;
        String uniqueNickname = baseNickname;
        int suffix = 1;

        while (userRepository.existsByNickname(uniqueNickname)) {
            uniqueNickname = baseNickname + "_" + providerId + "_" + suffix;
            suffix++;
        }

        return uniqueNickname;
    }

    private LoginResponse issueTokenResponse(User user) {
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

    private void validateKakaoProperties() {
        if (!StringUtils.hasText(kakaoClientId) || !StringUtils.hasText(kakaoRedirectUri)) {
            throw new KakaoLoginFailedException();
        }
    }
}

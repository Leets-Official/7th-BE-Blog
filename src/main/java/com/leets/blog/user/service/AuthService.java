package com.leets.blog.user.service;

import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.security.JwtTokenProvider;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.dto.KakaoOAuthResponse;
import com.leets.blog.user.oauth.KakaoOAuthClient;
import com.leets.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private static final String KAKAO_PROVIDER = "KAKAO";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuthClient kakaoOAuthClient;

    @Transactional
    public AuthResponse.UserInfo signUp(AuthRequest.SignUp request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                UserRole.USER
        );

        User savedUser = userRepository.save(user);
        return new AuthResponse.UserInfo(savedUser);
    }

    public AuthResponse.Login login(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        return createLoginResponse(user);
    }

    @Transactional
    public AuthResponse.Login kakaoLogin(AuthRequest.KakaoLogin request) {
        KakaoOAuthResponse.UserInfo kakaoUserInfo = kakaoOAuthClient.getUserInfo(request.getCode());
        String kakaoId = kakaoUserInfo.getId().toString();

        User user = userRepository.findByOauthProviderAndOauthId(KAKAO_PROVIDER, kakaoId)
                .orElseGet(() -> findOrCreateKakaoUser(kakaoUserInfo, kakaoId));

        return createLoginResponse(user);
    }

    public String reissueAccessToken(String refreshToken) {
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return createAccessToken(user);
    }

    public AuthResponse.UserInfo findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return new AuthResponse.UserInfo(user);
    }

    private User findOrCreateKakaoUser(KakaoOAuthResponse.UserInfo kakaoUserInfo, String kakaoId) {
        String email = resolveKakaoEmail(kakaoUserInfo, kakaoId);
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.linkOAuth(KAKAO_PROVIDER, kakaoId);
                    return user;
                })
                .orElseGet(() -> {
                    User user = new User(
                            email,
                            passwordEncoder.encode(UUID.randomUUID().toString()),
                            resolveKakaoNickname(kakaoUserInfo, kakaoId),
                            UserRole.USER
                    );
                    user.linkOAuth(KAKAO_PROVIDER, kakaoId);
                    return userRepository.save(user);
                });
    }

    private AuthResponse.Login createLoginResponse(User user) {
        String accessToken = createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        return new AuthResponse.Login(user, accessToken, refreshToken);
    }

    private String createAccessToken(User user) {
        return jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
    }

    private String resolveKakaoEmail(KakaoOAuthResponse.UserInfo kakaoUserInfo, String kakaoId) {
        if (StringUtils.hasText(kakaoUserInfo.getEmail())) {
            return kakaoUserInfo.getEmail();
        }
        return "kakao_" + kakaoId + "@kakao.local";
    }

    private String resolveKakaoNickname(KakaoOAuthResponse.UserInfo kakaoUserInfo, String kakaoId) {
        if (StringUtils.hasText(kakaoUserInfo.getNickname())) {
            return kakaoUserInfo.getNickname();
        }
        return "kakao_" + kakaoId;
    }
}

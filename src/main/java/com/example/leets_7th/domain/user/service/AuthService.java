package com.example.leets_7th.domain.user.service;


import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.jwt.JwtProvider;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.common.util.CookieUtil;
import com.example.leets_7th.domain.user.dto.request.LoginRequest;
import com.example.leets_7th.domain.user.dto.request.SignUpRequest;
import com.example.leets_7th.domain.user.dto.response.KakaoUserInfo;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final KakaoAuthService kakaoAuthService;
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new GeneralException(ErrorStatus.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.create(
                request.name(),
                request.gender(),
                request.email(),
                encodedPassword,
                request.age()
        );

        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public void login(LoginRequest request, HttpServletResponse response) {

        // 이메일 + 비밀번호 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        // JWT 발급
        String accessToken  = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);

        // 쿠키에 세팅
        cookieUtil.addAccessTokenCookie(response, accessToken);
        cookieUtil.addRefreshTokenCookie(response, refreshToken);
    }

    // 로그아웃
    public void logout(HttpServletResponse response) {
        cookieUtil.deleteAccessTokenCookie(response);
        cookieUtil.deleteRefreshTokenCookie(response);
    }

    // Access Token 재발급
    public void refresh(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 Refresh Token 추출
        String refreshToken = cookieUtil.getRefreshToken(request)
                .orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_TOKEN));

        // Refresh Token 검증
        jwtProvider.validateToken(refreshToken);

        // 인증 객체 추출
        Authentication authentication = jwtProvider.getAuthentication(refreshToken);

        String newAccessToken  = jwtProvider.createAccessToken(authentication);
        String newRefreshToken = jwtProvider.createRefreshToken(authentication);

        cookieUtil.addAccessTokenCookie(response, newAccessToken);
        cookieUtil.addRefreshTokenCookie(response, newRefreshToken);
    }

    @Transactional
    public void kakaoLogin(String code, HttpServletResponse response) {

        String kakaoAccessToken = kakaoAuthService.getAccessToken(code);

        KakaoUserInfo userInfo = kakaoAuthService.getUserInfo(kakaoAccessToken);

        User user = userRepository.findByEmail(userInfo.email())
                .orElseGet(() -> userRepository.save(User.createByKakao(
                        userInfo.nickname(),
                        userInfo.email(),
                        userInfo.kakaoId()
                )));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );

        String accessToken  = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);

        cookieUtil.addAccessTokenCookie(response, accessToken);
        cookieUtil.addRefreshTokenCookie(response, refreshToken);

        log.info("카카오 인가코드: {}", code);
    }
}

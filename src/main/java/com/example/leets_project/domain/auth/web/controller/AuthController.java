package com.example.leets_project.domain.auth.web.controller;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.GlobalResponse;
import com.example.leets_project.common.response.SuccessCode;
import com.example.leets_project.common.security.jwt.JwtProperties;
import com.example.leets_project.common.util.CookieUtil;
import com.example.leets_project.domain.auth.service.AuthService;
import com.example.leets_project.domain.auth.web.dto.KakaoLoginRequest;
import com.example.leets_project.domain.auth.web.dto.LoginRequest;
import com.example.leets_project.domain.auth.web.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "AUTH API", description = "회원가입, 로그인, 토큰 재발급, 로그아웃 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final JwtProperties jwtProperties;

    // 회원가입
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 입력하여 새로운 계정을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse> signUp(@RequestBody @Valid SignUpRequest request) {

        authService.signUp(request);
        return GlobalResponse.onSuccess(SuccessCode.USER_CREATE);
    }

    // 로그인
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다. AccessToken은 Body, RefreshToken은 HttpOnly 쿠키로 반환됩니다.")
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) {

        AuthService.LoginResult result = authService.login(request);
        addRefreshTokenCookie(response, result.refreshToken());

        return GlobalResponse.onSuccess(SuccessCode.AUTH_LOGIN, result.loginResponse());
    }

    // 토큰 재발급
    @Operation(summary = "토큰 재발급", description = "HttpOnly 쿠키의 RefreshToken으로 새로운 AccessToken을 발급합니다. RefreshToken도 Rotation됩니다.")
    @PostMapping("/reissue")
    public ResponseEntity<GlobalResponse> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = cookieUtil.extractRefreshToken(request);

        AuthService.ReissueResult result = authService.reissue(refreshToken);
        addRefreshTokenCookie(response, result.newRefreshToken());

        return GlobalResponse.onSuccess(SuccessCode.AUTH_REISSUE, result.tokenResponse());
    }

    // 로그아웃
    @Operation(summary = "로그아웃", description = "RefreshToken을 폐기하고 쿠키를 삭제합니다.")
    @PostMapping("/logout")
    public ResponseEntity<GlobalResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = cookieUtil.extractRefreshToken(request);
            authService.logout(refreshToken);
        } catch (GeneralException e) {
            log.warn("로그아웃 - 토큰 오류: {}", e.getMessage());
        }
        cookieUtil.deleteRefreshTokenCookie(response);
        return GlobalResponse.onSuccess(SuccessCode.AUTH_LOGOUT);
    }

    // Kakao 로그인
    @PostMapping("/kakao/login")
    public ResponseEntity<GlobalResponse> kakaoLogin(@RequestBody @Valid KakaoLoginRequest request,
                                                     HttpServletResponse response
    ) {
        AuthService.LoginResult result = authService.loginWithKakao(request);
        addRefreshTokenCookie(response, result.refreshToken());

        return GlobalResponse.onSuccess(SuccessCode.AUTH_LOGIN, result.loginResponse());
    }

    // RefreshToken 쿠키 설정
    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {

        cookieUtil.addRefreshTokenCookie( response, refreshToken,jwtProperties.getRefreshTokenExpirationMillis());
    }
}
package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.security.CookieUtils;
import com.leets.blog.security.JwtProperties;
import com.leets.blog.security.JwtTokenProvider;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "회원가입/로그인 및 내 정보 조회 API")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "이메일/비밀번호/닉네임으로 회원가입합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> signUp(@Valid @RequestBody AuthRequest.SignUp request) {
        AuthResponse.UserInfo response = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "이메일/비밀번호로 로그인하고 access/refresh 토큰을 쿠키로 발급합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.Login>> login(
            @Valid @RequestBody AuthRequest.Login request,
            HttpServletResponse response
    ) {
        AuthResponse.Login loginResponse = authService.login(request);

        String accessToken = jwtTokenProvider.createAccessToken(
                loginResponse.getUserId(),
                loginResponse.getEmail(),
                loginResponse.getRole().name()
        );
        String refreshToken = jwtTokenProvider.createRefreshToken(loginResponse.getUserId());

        response.addHeader(HttpHeaders.SET_COOKIE,
                CookieUtils.createCookie(
                        jwtProperties.getAccessCookieName(),
                        accessToken,
                        jwtProperties.getAccessTokenExpirationSeconds(),
                        jwtProperties
                ).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                CookieUtils.createCookie(
                        jwtProperties.getRefreshCookieName(),
                        refreshToken,
                        jwtProperties.getRefreshTokenExpirationSeconds(),
                        jwtProperties
                ).toString());

        return ResponseEntity.ok(BaseResponse.ok(loginResponse));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "토큰 재발급",
            description = "Refresh token을 이용하여 새로운 access token을 쿠키에 발급합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = CookieUtils.getCookieValue(request, jwtProperties.getRefreshCookieName());
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(BaseResponse.<AuthResponse.UserInfo>builder()
                    .message("Refresh token이 유효하지 않습니다.")
                    .build());
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        AuthResponse.UserInfo userInfo = authService.findUserById(userId);
        String accessToken = jwtTokenProvider.createAccessToken(userInfo.getUserId(), userInfo.getEmail(), userInfo.getRole().name());

        response.addHeader(HttpHeaders.SET_COOKIE,
                CookieUtils.createCookie(
                        jwtProperties.getAccessCookieName(),
                        accessToken,
                        jwtProperties.getAccessTokenExpirationSeconds(),
                        jwtProperties
                ).toString());

        return ResponseEntity.ok(BaseResponse.ok(userInfo));
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 정보 조회",
            description = "Access token 쿠키 기준으로 내 정보를 조회합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> me(
            @Parameter(hidden = true)
            @CurrentUser AuthUser authUser
    ) {
        AuthResponse.UserInfo response = new AuthResponse.UserInfo(
                authUser.getUserId(),
                authUser.getEmail(),
                authUser.getNickname(),
                authUser.getRole()
        );
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}

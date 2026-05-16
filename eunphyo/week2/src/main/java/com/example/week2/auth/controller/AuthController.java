package com.example.week2.auth.controller;

import com.example.week2.auth.dto.AuthRequest;
import com.example.week2.global.response.ApiResponse;
import com.example.week2.global.response.SuccessCode;
import com.example.week2.auth.dto.AuthResponse;
import com.example.week2.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs{

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> signup(
            @Valid @RequestBody AuthRequest.SignupRequest request
    ) {
        authService.signup(request);

        return ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, null);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse.AccessToken> login(
            @Valid @RequestBody AuthRequest.LoginRequest request,
            HttpServletResponse servletResponse
    ) {
        AuthResponse.TokenResult tokenResponse = authService.login(request);

        setRefreshTokenCookie(servletResponse, tokenResponse.refreshToken());

        AuthResponse.AccessToken response =
                new AuthResponse.AccessToken(tokenResponse.accessToken());

        return ApiResponse.success(SuccessCode.LOGIN_SUCCESS, response);
    }

    @PostMapping("/reissue")
    public ApiResponse<AuthResponse.AccessToken> reissue(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse servletResponse
    ) {
        AuthResponse.TokenResult tokenResponse = authService.reissue(refreshToken);

        setRefreshTokenCookie(servletResponse, tokenResponse.refreshToken());

        AuthResponse.AccessToken response =
                new AuthResponse.AccessToken(tokenResponse.accessToken());


        return ApiResponse.success(SuccessCode.TOKEN_REISSUE_SUCCESS, response);
    }

    private void setRefreshTokenCookie(
            HttpServletResponse response,
            String refreshToken
    ) {

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/auth")
                .maxAge(60 * 60 * 24 * 7)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
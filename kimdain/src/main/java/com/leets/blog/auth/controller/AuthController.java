package com.leets.blog.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.leets.blog.auth.service.AuthService;
import com.leets.blog.auth.dto.SignupRequest;
import com.leets.blog.auth.dto.LoginRequest;
import com.leets.blog.auth.dto.TokenResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    // 카카오 로그인
    @GetMapping("/kakao/callback")
    public ResponseEntity<TokenResponse> kakaoLogin(@RequestParam String code) {
        TokenResponse tokenResponse = authService.kakaoLogin(code);
        return ResponseEntity.ok(tokenResponse);
    }
}
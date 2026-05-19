package com.example.springbootassignment.controller;

import com.example.springbootassignment.dto.LoginRequest;
import com.example.springbootassignment.dto.SignupRequest;
import com.example.springbootassignment.dto.TokenResponse;
import com.example.springbootassignment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Authentication authentication) {

        OAuth2User oAuth2User =
                (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes =
                oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.get("kakao_account");

        String email =
                (String) kakaoAccount.get("email");

        return "카카오 로그인 성공! 이메일 : " + email;
    }
}

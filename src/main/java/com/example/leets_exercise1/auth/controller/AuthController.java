package com.example.leets_exercise1.auth.controller;

import com.example.leets_exercise1.auth.dto.request.LoginRequest;
import com.example.leets_exercise1.auth.dto.request.SignUpRequest;
import com.example.leets_exercise1.auth.dto.request.TokenReissueRequest;
import com.example.leets_exercise1.auth.dto.response.LoginResponse;
import com.example.leets_exercise1.auth.dto.response.SignUpResponse;
import com.example.leets_exercise1.auth.dto.response.TokenReissueResponse;
import com.example.leets_exercise1.auth.service.AuthService;
import com.example.leets_exercise1.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("2010", "회원가입에 성공했습니다.", authService.signUp(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("2000", "로그인에 성공했습니다.", authService.login(request)));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(@Valid @RequestBody TokenReissueRequest request) {
        return ResponseEntity.ok(ApiResponse.success("2001", "access token 재발급에 성공했습니다.", authService.reissue(request)));
    }
}

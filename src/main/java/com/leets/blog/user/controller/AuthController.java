package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.dto.AuthRequest;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.auth.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> signUp(@Valid @RequestBody AuthRequest.SignUp request) {
        AuthResponse.UserInfo response = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(response));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse.Login>> login(@Valid @RequestBody AuthRequest.Login request) {
        AuthResponse.Login response = authService.login(request);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<AuthUser>> me(@CurrentUser AuthUser authUser) {
        return ResponseEntity.ok(BaseResponse.ok(authUser));
    }
}

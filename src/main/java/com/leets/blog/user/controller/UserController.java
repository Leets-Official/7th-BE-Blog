package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> findById(@PathVariable Long userId) {
        AuthResponse.UserInfo response = authService.findUserById(userId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}


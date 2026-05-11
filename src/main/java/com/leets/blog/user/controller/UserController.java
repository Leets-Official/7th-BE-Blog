package com.leets.blog.user.controller;

import com.leets.blog.global.common.BaseResponse;
import com.leets.blog.user.dto.AuthResponse;
import com.leets.blog.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "유저 조회 API")
public class UserController {

    private final AuthService authService;

    @GetMapping("/{userId}")
    @Operation(
            summary = "유저 단건 조회",
            description = "유저 ID로 유저 정보를 조회합니다."
    )
    public ResponseEntity<BaseResponse<AuthResponse.UserInfo>> findById(@PathVariable Long userId) {
        AuthResponse.UserInfo response = authService.findUserById(userId);
        return ResponseEntity.ok(BaseResponse.ok(response));
    }
}


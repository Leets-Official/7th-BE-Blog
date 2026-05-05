package com.example.demo.user.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "유저 생성 API",
            description = "새로운 유저를 생성합니다."
    )
    @PostMapping
    public ApiResponse<Map<String, Long>> create(
            @RequestBody @Valid UserCreateRequest request) {

        return ResponseUtil.success(
                BaseCode.USER_CREATE_SUCCESS,
                Map.of("userId", userService.createUser(request))
        );
    }
}

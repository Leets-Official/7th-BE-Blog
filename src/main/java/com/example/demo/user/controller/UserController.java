package com.example.demo.user.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.ResponseUtil;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Map<String, Long>> create(@RequestBody @Valid UserCreateRequest request) {
        return ResponseUtil.success(
                BaseCode.USER_CREATE_SUCCESS,
                Map.of("userId", userService.createUser(request))
        );
    }
}

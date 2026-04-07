package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.UserCreateRequest;
import com.example.blog.domain.user.dto.UserResponse;
import com.example.blog.domain.user.dto.UserUpdateRequest;
import com.example.blog.domain.user.service.UserService;
import com.example.blog.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.success(userService.create(request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> findById(@PathVariable Long userId) {
        return ApiResponse.success(userService.findById(userId));
    }

    @PatchMapping("/{userId}")
    public ApiResponse<UserResponse> update(
        @PathVariable Long userId,
        @RequestBody @Valid UserUpdateRequest request
    ) {
        return ApiResponse.success(userService.update(userId, request));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}

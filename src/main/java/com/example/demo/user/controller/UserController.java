package com.example.demo.user.controller;

import com.example.demo.global.exception.ApiResponse;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Long> create(@RequestBody @Valid UserCreateRequest request) {
        Long id = userService.createUser(request);

        return ApiResponse.<Long>builder()
                .success(true)
                .data(id)
                .build();
    }
}

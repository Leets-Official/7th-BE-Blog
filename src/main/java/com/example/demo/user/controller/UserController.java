package com.example.demo.user.controller;

import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public Long create(@RequestBody UserCreateRequest request) {
        User user = new User(request.getName(), request.getEmail());
        return userRepository.save(user).getId();
    }
}

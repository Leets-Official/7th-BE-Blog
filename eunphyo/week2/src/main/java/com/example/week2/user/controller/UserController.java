package com.example.week2.user.controller;

import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestParam String name) {

        User user = User.builder()
                .name(name)
                .build();

        return userRepository.save(user);
    }
}
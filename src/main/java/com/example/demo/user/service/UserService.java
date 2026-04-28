package com.example.demo.user.service;

import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(UserCreateRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail()
        );

        return userRepository.save(user).getId();
    }
}
package com.example.blog.domain.user.dto;

import com.example.blog.domain.user.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
    Long userId,
    String username,
    String email,
    String profileUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getProfileUrl(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}

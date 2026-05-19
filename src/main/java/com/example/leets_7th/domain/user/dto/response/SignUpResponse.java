package com.example.leets_7th.domain.user.dto.response;

import com.example.leets_7th.domain.user.entity.User;

public record SignUpResponse(
        Long userId,
        String email,
        String name
) {
    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}

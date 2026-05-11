package com.example.leets_7th.domain.user.dto.response;

import com.example.leets_7th.domain.user.entity.User;

public record LoginResponse(
        Long userId,
        String email,
        String name
) {
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}

package com.example.leets7th.global.auth.dto.res;

import com.example.leets7th.domain.user.entity.User;
import lombok.Builder;

@Builder
public record AuthResponseDTO(
        Long userId,
        String email,
        String nickname
) {
    public static AuthResponseDTO from(User user) {
        return AuthResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}

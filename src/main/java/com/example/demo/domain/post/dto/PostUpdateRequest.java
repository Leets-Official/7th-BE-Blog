package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        String title,
        String content,
        String imageUrl
) {
}
package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotNull;

public record PostDeleteRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long userId
) {
}

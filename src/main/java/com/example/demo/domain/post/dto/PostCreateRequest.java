package com.example.demo.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        @NotBlank(message = "title은 필수입니다.")
        String title,

        @NotBlank(message = "content는 필수입니다.")
        String content,

        String imageUrl
) {
}

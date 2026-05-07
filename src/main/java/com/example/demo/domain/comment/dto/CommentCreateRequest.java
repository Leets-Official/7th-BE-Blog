package com.example.demo.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        @NotBlank(message = "content는 필수입니다.")
        String content
) {
}

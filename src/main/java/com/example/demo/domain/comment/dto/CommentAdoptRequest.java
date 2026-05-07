package com.example.demo.domain.comment.dto;

import jakarta.validation.constraints.NotNull;

public record CommentAdoptRequest(
        @NotNull(message = "userId는 필수입니다.")
        Long userId
) {
}

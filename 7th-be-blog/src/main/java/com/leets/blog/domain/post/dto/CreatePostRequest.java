package com.leets.blog.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
        String title,
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        @NotNull(message = "작성자 ID는 필수입니다.")
        Long userId
) {
}

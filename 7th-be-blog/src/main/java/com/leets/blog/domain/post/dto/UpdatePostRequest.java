package com.leets.blog.domain.post.dto;

import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
        String title,
        String content
) {
}

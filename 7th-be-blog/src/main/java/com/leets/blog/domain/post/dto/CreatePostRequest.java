package com.leets.blog.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @Schema(description = "게시글 제목", example = "스프링부트 Swagger 적용기")
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
        String title,
        @Schema(description = "게시글 본문", example = "Swagger UI를 프로젝트에 적용한 과정을 정리합니다.")
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        @Schema(description = "작성자 ID", example = "1")
        @NotNull(message = "작성자 ID는 필수입니다.")
        Long userId
) {
}

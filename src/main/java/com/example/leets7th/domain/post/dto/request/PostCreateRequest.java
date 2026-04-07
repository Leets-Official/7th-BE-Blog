package com.example.leets7th.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "게시글 생성 요청")
public record PostCreateRequest(
        @Schema(description = "카테고리 ID", example = "1")
        @NotNull(message = "카테고리는 필수입니다.") Long categoryId,

        @Schema(description = "제목", example = "스프링 공부 1일차")
        @NotBlank(message = "제목은 필수입니다.") String title,

        @Schema(description = "내용", example = "오늘은 JPA를 배웠다.")
        @NotBlank(message = "내용은 필수입니다.") String content
) {
}

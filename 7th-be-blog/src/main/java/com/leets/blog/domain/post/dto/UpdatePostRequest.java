package com.leets.blog.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @Schema(description = "수정할 게시글 제목", example = "스프링부트 Swagger 적용기 - 수정본")
        @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
        String title,
        @Schema(description = "수정할 게시글 본문", example = "문서화 과정을 최신 기준으로 보완했습니다.")
        String content
) {
}

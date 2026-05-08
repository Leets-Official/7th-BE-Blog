package com.example.demo.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "댓글 생성 요청")
public record CommentCreateRequest(
        @Schema(description = "댓글 작성자 ID", example = "2")
        @NotNull(message = "userId는 필수입니다.")
        Long userId,

        @Schema(description = "댓글 내용", example = "좋은 글이네요!")
        @NotBlank(message = "content는 필수입니다.")
        String content
) {
}

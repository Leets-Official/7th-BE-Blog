package com.leets.blog.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequest {

    @Getter
    @NoArgsConstructor
    @Schema(name = "CommentCreateRequest")
    public static class Create {
        @NotBlank(message = "댓글 내용은 필수입니다.")
        @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
        private String content;
    }
}

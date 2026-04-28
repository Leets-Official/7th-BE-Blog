package com.example.leets_7th.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(

        @Schema(example = "내용")
        @NotBlank(message = "내용은 공백일 수 없습니다")
        String comment
        //todo 댓글에도 이미지 추가
        //todo 대댓글
) {
}

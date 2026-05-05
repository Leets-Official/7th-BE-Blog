package com.example.demo.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Schema(description = "댓글 수정 요청 DTO")
public class CommentUpdateRequest {

    @Schema(description = "수정할 댓글 내용", example = "수정된 댓글입니다.")
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}

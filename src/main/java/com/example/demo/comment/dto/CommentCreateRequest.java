package com.example.demo.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Schema(description = "댓글 생성 요청 DTO")
public class CommentCreateRequest {

    @Schema(description = "유저 ID", example = "1")
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @Schema(description = "게시글 ID", example = "10")
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long postId;

    @Schema(description = "댓글 내용", example = "좋은 글이네요!")
    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}

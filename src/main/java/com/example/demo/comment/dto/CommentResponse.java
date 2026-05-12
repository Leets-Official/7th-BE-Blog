package com.example.demo.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "댓글 응답 DTO")
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long id;

    @Schema(description = "댓글 내용", example = "좋은 글이네요!")
    private String content;

    @Schema(description = "작성자 유저 ID", example = "5")
    private Long userId;
}
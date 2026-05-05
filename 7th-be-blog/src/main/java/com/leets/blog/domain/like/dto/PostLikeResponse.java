package com.leets.blog.domain.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 좋아요 응답")
public class PostLikeResponse {

    @Schema(description = "좋아요 ID", example = "1")
    private Long likeId;
    @Schema(description = "게시글 ID", example = "10")
    private Long postId;
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "좋아요 생성 시각", example = "2026-05-05T14:30:00")
    private LocalDateTime createdAt;
}

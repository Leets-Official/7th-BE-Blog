package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "게시글 목록 항목")
public record PostListItemResponse(
        @Schema(description = "게시글 ID", example = "1")
        Long postId,
        @Schema(description = "게시글 제목", example = "첫 번째 게시글")
        String title,
        @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
        String content,
        @Schema(description = "썸네일 이미지 URL", example = "https://example.com/thumb.png")
        String thumbnailImageUrl,
        @Schema(description = "게시글 상태", example = "ACTIVE")
        PostStatus status,
        @Schema(description = "작성자 이름", example = "tester")
        String author,
        @Schema(description = "생성 시각", example = "2026-05-05T12:00:00")
        LocalDateTime createdAt
) {
}

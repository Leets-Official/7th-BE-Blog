package com.leets.blog.comment.dto;

import com.leets.blog.comment.domain.Comment;
import com.leets.blog.comment.domain.CommentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    @Schema(description = "댓글 ID", example = "1")
    private final Long id;
    @Schema(description = "게시글 ID", example = "1")
    private final Long postId;
    @Schema(description = "작성자 사용자 ID", example = "1")
    private final Long authorId;
    @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
    private final String content;
    @Schema(description = "댓글 상태", example = "ACTIVE")
    private final CommentStatus status;
    @Schema(description = "채택 여부", example = "false")
    private final boolean accepted;
    @Schema(description = "생성 시각(ISO-8601)", example = "2026-05-06T00:00:00")
    private final LocalDateTime createdAt;
    @Schema(description = "수정 시각(ISO-8601)", example = "2026-05-06T00:10:00")
    private final LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.authorId = comment.getUser().getId();
        this.content = comment.getContent();
        this.status = comment.getStatus();
        this.accepted = comment.isAccepted();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}

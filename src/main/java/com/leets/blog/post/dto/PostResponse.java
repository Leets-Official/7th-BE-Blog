package com.leets.blog.post.dto;

import com.leets.blog.post.domain.Post;
import com.leets.blog.post.domain.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {
    @Schema(description = "게시글 ID", example = "1")
    private Long id;
    @Schema(description = "작성자 사용자 ID", example = "1")
    private Long authorId;
    @Schema(description = "게시글 제목", example = "스웨거 문서화")
    private String title;
    @Schema(description = "게시글 내용", example = "API 요약/설명을 추가합니다.")
    private String content;
    @Schema(description = "게시글 상태", example = "ACTIVE")
    private PostStatus status;
    @Schema(description = "생성 시각(ISO-8601)", example = "2026-05-06T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정 시각(ISO-8601)", example = "2026-05-06T00:10:00")
    private LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.authorId = post.getUser() == null ? null : post.getUser().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
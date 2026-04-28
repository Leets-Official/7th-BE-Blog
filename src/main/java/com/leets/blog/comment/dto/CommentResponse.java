package com.leets.blog.comment.dto;

import com.leets.blog.comment.domain.Comment;
import com.leets.blog.comment.domain.CommentStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final Long postId;
    private final Long authorId;
    private final String content;
    private final CommentStatus status;
    private final boolean accepted;
    private final LocalDateTime createdAt;
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

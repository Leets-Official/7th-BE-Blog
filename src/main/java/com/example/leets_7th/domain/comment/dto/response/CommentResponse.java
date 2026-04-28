package com.example.leets_7th.domain.comment.dto.response;

import com.example.leets_7th.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String content,
        String author,
        Integer likeCount,
        LocalDateTime createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getName(),
                comment.getLikeCount(),
                comment.getCreatedAt()
        );
    }
}

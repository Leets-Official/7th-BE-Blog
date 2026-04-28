package com.example.blog.domain.comment.dto;

import com.example.blog.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
    Long commentId,
    Long postId,
    Long userId,
    String username,
    String content,
    boolean accepted,
    Long parentCommentId,
    List<CommentResponse> replies,
    LocalDateTime createdAt
) {

    public static CommentResponse from(Comment comment) {
        List<CommentResponse> replies = comment.getReplies().stream()
            .map(CommentResponse::from)
            .toList();
        return new CommentResponse(
            comment.getId(),
            comment.getPost().getId(),
            comment.getUser().getId(),
            comment.getUser().getUsername(),
            comment.getContent(),
            comment.isAccepted(),
            comment.getParentComment() != null ? comment.getParentComment().getId() : null,
            replies,
            comment.getCreatedAt()
        );
    }
}

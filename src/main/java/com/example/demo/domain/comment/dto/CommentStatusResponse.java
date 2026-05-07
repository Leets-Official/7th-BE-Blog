package com.example.demo.domain.comment.dto;

import com.example.demo.domain.comment.entity.CommentStatus;

public record CommentStatusResponse(
        Long commentId,
        CommentStatus status
) {
}

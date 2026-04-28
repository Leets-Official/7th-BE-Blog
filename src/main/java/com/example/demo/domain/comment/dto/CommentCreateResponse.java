package com.example.demo.domain.comment.dto;

import com.example.demo.domain.comment.entity.CommentStatus;

public record CommentCreateResponse(
        Long commentId,
        CommentStatus status
) {
}

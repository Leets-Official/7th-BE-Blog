package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.PostStatus;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long postId,
        String title,
        String content,
        String imageUrl,
        PostStatus status,
        Long authorId,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package com.example.demo.domain.post.dto;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long postId,
        String title,
        String content,
        String imageUrl,
        Long authorId,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

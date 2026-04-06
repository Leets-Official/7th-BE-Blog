package com.example.demo.domain.post.dto;

import java.time.LocalDateTime;

public record PostListItemResponse(
        Long postId,
        String title,
        String content,
        String thumbnailImageUrl,
        String author,
        LocalDateTime createdAt
) {
}

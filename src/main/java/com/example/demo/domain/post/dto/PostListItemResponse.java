package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.PostStatus;

import java.time.LocalDateTime;

public record PostListItemResponse(
        Long postId,
        String title,
        String content,
        String thumbnailImageUrl,
        PostStatus status,
        String author,
        LocalDateTime createdAt
) {
}

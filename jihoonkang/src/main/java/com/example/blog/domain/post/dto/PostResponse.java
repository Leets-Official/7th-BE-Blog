package com.example.blog.domain.post.dto;

import com.example.blog.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
    Long postId,
    Long userId,
    String username,
    String title,
    String content,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getUser().getId(),
            post.getUser().getUsername(),
            post.getTitle(),
            post.getContent(),
            post.getStatus().name(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}

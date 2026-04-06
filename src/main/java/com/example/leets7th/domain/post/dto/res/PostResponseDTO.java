package com.example.leets7th.domain.post.dto.res;


import lombok.Builder;

import java.time.LocalDateTime;

public class PostResponseDTO {
    @Builder
    public record PostListResDTO (
            Long postId,
            String title,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record PostDetailResDTO (
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record CreatePostResDTO (
            Long postId,
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}
}

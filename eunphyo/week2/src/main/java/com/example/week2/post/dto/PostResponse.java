package com.example.week2.post.dto;

import com.example.week2.post.entity.Post;
import lombok.Builder;
import java.time.LocalDateTime;


public class PostResponse{
    @Builder
    public record PostListResponse(
            Long postId,
            String title,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record PostDetailResponse (
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record CreatePostResponse (
            Long postId,
            String title,
            String content,
            String nickname,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}
}
package com.example.week2.post.dto;

import com.example.week2.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponse {

    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userId(post.getUser().getId())
                .build();
    }
}
package com.example.leets_exercise1.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private String description;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
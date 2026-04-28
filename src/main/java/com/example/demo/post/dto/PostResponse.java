package com.example.demo.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String description;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

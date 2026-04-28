package com.example.leets_exercise1.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostCreateResponse {
    private Long postId;
    private String title;
    private String authorNickname;
    private LocalDateTime createdAt;
}
package com.example.demo.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long userId;
    private Long postId;
    private String content;
}

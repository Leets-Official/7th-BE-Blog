package com.example.demo.domain.post.dto;

public record PostCreateResponse(
        Long postId,
        String message
) {
}

package com.example.leets_7th.domain.post.dto.response;

import com.example.leets_7th.domain.post.enums.PostVisibility;

import java.time.LocalDateTime;

public record UpdatePostResponse(
        Long postId,
        PostVisibility postVisibility,
        LocalDateTime updatedAt

) {}

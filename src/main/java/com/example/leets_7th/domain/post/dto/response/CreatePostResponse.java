package com.example.leets_7th.domain.post.dto.response;

import com.example.leets_7th.domain.post.enums.PostVisibility;

public record CreatePostResponse(
        Long postId,
        String thumbnailUrl,
        PostVisibility postVisibility
) {}

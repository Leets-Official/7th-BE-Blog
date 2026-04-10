package com.example.leets_7th.domain.post.dto.response;

import java.time.LocalDateTime;

public record UpdatePostResponse(
        Long postId,
        LocalDateTime updatedAt
) {}

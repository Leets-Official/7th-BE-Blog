package com.leets.blog.dto;

import java.time.LocalDateTime;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        String authorNickname,
        String createdAt
) {}
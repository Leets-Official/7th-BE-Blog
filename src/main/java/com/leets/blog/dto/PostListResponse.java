package com.leets.blog.dto;

import java.util.List;

public record PostListResponse(
        List<PostItem> posts,
        long totalCount,
        int page
) {
    public record PostItem(
            Long id,
            String title,
            String createdAt,
            Long authorId
    ) {}
}
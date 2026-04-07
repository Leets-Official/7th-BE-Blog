package com.example.leets_7th.domain.post.dto.response;

import java.util.List;

public record GetPostResponse(
        List<PostSummary> posts,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static GetPostResponse of(
            List<PostSummary> posts,
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
        return new GetPostResponse(posts, page, size, totalElements, totalPages);
    }
}

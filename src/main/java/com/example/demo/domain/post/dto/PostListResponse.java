package com.example.demo.domain.post.dto;

import java.util.List;

public record PostListResponse(
        List<PostListItemResponse> posts,
        PageInfoResponse pageInfo
) {
}

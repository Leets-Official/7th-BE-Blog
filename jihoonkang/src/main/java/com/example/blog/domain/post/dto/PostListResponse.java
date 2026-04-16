package com.example.blog.domain.post.dto;

import java.util.List;

public record PostListResponse(
    List<PostResponse> items,
    int page,
    int size,
    long totalElements
) {}

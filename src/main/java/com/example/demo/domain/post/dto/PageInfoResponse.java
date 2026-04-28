package com.example.demo.domain.post.dto;

public record PageInfoResponse(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
}

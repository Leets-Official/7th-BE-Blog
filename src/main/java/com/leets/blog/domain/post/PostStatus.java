package com.leets.blog.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    DRAFT("임시 저장"),
    PUBLISHED("발행 완료");

    private final String description;
}

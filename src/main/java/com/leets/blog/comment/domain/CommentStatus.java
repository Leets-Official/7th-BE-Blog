package com.leets.blog.comment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentStatus {
    ACTIVE("노출 중"),
    HIDDEN("숨김 처리");

    private final String description;
}

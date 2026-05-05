package com.example.leets_7th.domain.post.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PostVisibility {
    PUBLIC,
    FRIENDS,
    PRIVATE
}

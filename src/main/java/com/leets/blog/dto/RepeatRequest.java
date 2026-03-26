package com.leets.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RepeatRequest {
    private String content;     // 요청으로 받을 문자열
}

package com.leets.blog.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RepeatRequest {
    @Schema(description = "반복할 문자열", example = "hello")
    private String content;     // 요청으로 받을 문자열
}

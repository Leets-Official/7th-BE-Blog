package com.leets.blog.health.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepeatResponse {

    @JsonProperty("string_one")
    @Schema(description = "입력 문자열 1회 반복 결과", example = "hello")
    private String stringOne;

    @JsonProperty("string_two")
    @Schema(description = "입력 문자열 2회 반복 결과", example = "hellohello")
    private String stringTwo;
}

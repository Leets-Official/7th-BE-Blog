package com.leets.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepeatResponse {
    @JsonProperty("string_one")
    private String stringOne;

    @JsonProperty("string_two")
    private String stringTwo;
}
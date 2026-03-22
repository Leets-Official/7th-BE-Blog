package com.leets.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StringResponseDto {

    private String string_one;
    private String string_two;

    public StringResponseDto(String text) {
        this.string_one = text;
        this.string_two = text;
    }

    public String getString_one() { return string_one; }
    public String getString_two() { return string_two; }
}
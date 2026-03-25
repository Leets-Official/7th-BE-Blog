package com.leets.assignment.dto;

import lombok.Getter;

@Getter
public class RepeatResponseDto {
    private final String string_one;
    private final String string_two;

    public RepeatResponseDto(String value) {
        this.string_one = value;
        this.string_two = value;
    }
}

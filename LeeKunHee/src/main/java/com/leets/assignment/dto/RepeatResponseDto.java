package com.leets.assignment.dto;

public class RepeatResponseDto {
    private final String string_one;
    private final String string_two;

    public RepeatResponseDto(String value) {
        this.string_one = value;
        this.string_two = value;
    }

    public String getString_one() { return string_one; }
    public String getString_two() { return string_two; }
}

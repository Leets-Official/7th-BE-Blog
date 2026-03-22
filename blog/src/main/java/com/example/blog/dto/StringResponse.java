package com.example.blog.dto;

public class StringResponse {
    private String string_one;
    private String string_two;

    public StringResponse(String string_one, String string_two) {
        this.string_one = string_one;
        this.string_two = string_two;
    }

    public String getString_one() {
        return string_one;
    }

    public String getString_two() {
        return string_two;
    }
}

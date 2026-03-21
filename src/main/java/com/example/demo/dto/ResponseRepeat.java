package com.example.demo.dto;

import lombok.Getter;

@Getter
public class ResponseRepeat {

    private final String string_one;
    private final String string_two;

    public ResponseRepeat(String string_one, String string_two) {
        this.string_one = string_one;
        this.string_two = string_two;
    }
}

package com.example.leets_exercise1.dto;

public class RepeatResponse {
    private String string_one;
    private String string_two;

    public RepeatResponse() {
    }

    public RepeatResponse(String string_one, String string_two) {
        this.string_one = string_one;
        this.string_two = string_two;
    }

    public String getString_one() {
        return string_one;
    }

    public void setString_one(String string_one) {
        this.string_one = string_one;
    }

    public String getString_two() {
        return string_two;
    }

    public void setString_two(String string_two) {
        this.string_two = string_two;
    }
}
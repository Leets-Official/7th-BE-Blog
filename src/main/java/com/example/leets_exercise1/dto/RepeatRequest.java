package com.example.leets_exercise1.dto;

import jakarta.validation.constraints.NotBlank;

public class RepeatRequest {

    @NotBlank(message = "value는 비어 있을 수 없습니다.")
    private String value;

    public RepeatRequest() {
    }

    public RepeatRequest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
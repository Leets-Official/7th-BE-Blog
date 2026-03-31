package com.example.demo1.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StringResponseDto {
    private String string_one;
    private String string_two;
}
package com.example.demo1.service;

import com.example.demo1.dto.StringRequestDto;
import com.example.demo1.dto.StringResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    public String getHealthStatus() {
        return "ok";
    }

    public StringResponseDto repeatString(StringRequestDto requestDto) {
        String original = requestDto.getValue();

        if (original == null || original.isBlank()) {
            throw new IllegalArgumentException("입력 값이 비어있습니다.");
        }

        return StringResponseDto.builder()
                .string_one(original)
                .string_two(original)
                .build();
    }
}
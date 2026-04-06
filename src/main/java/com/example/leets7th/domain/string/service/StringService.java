package com.example.leets7th.domain.string.service;

import com.example.leets7th.domain.string.dto.StringResponseDto;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponseDto repeatString(String value) {
        return new StringResponseDto(value, value);
    }
}

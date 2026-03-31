package com.example.leets7th.service;

import com.example.leets7th.dto.StringResponseDto;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public StringResponseDto repeatString(String value) {
        return new StringResponseDto(value, value);
    }
}
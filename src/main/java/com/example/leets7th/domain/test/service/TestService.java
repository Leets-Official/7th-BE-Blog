package com.example.leets7th.domain.test.service;

import com.example.leets7th.domain.test.dto.TestResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {
    public TestResDTO.TestDTO getTestValue(String value) {
        return TestResDTO.TestDTO.builder()
                .string_one(value)
                .string_two(value)
                .build();
    }
}

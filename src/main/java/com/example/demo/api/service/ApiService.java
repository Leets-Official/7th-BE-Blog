package com.example.demo.api.service;

import com.example.demo.api.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    public RepeatResponse repeat(String text) {
        return RepeatResponse.builder()
                .result(text + text)
                .build();
    }
}

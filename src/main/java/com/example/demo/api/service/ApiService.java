package com.example.demo.api.service;

import com.example.demo.api.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    //문자열 복사
    public RepeatResponse repeat(String text) {
        return RepeatResponse.builder()
                .result(text + text)
                .build();
    }
}

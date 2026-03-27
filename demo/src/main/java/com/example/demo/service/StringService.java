package com.example.demo.service;

import com.example.demo.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponse repeatString(String text) {
        return new StringResponse(text, text);
    }
}

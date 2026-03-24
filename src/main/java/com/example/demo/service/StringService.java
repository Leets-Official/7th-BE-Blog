package com.example.demo.service;

import com.example.demo.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public RepeatResponse repeatTwice(String text) {
        return new RepeatResponse(text, text);
    }
}

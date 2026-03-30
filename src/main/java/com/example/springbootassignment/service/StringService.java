package com.example.springbootassignment.service;

import com.example.springbootassignment.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponse repeatString(String text) {
        return new StringResponse(text, text);
    }

}
package com.example.leets_exercise1.service;

import com.example.leets_exercise1.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class RepeatService {

    public RepeatResponse repeat(String value) {
        return new RepeatResponse(value, value);
    }
}
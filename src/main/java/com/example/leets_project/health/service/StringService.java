package com.example.leets_project.health.service;

import com.example.leets_project.health.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponse repeat(String value) {

        return new StringResponse(value, value);
    }
}

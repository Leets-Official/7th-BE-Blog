package com.example.leets_project.domain.health.service;

import com.example.leets_project.domain.health.dto.StringResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponse repeat(String value) {

        return new StringResponse(value, value);
    }
}

package com.leets.blog.service;

import org.springframework.stereotype.Service;

@Service
public class StringService {
    public String getOriginalString(String input) {
        return input;       // 입력받은 문자열 + 문자열
    }
}

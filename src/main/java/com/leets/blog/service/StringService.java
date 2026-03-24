package com.leets.blog.service;

import com.leets.blog.dto.RepeatReq;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    public String repeatString(String input) {
        return input + input;       // 입력받은 문자열 + 문자열
    }
}

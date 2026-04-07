package com.leets.blog.health.service;

import com.leets.blog.health.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    public RepeatResponse repeat(String input) {
        return new RepeatResponse(input, input);
    }
}

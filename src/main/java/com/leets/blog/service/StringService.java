package com.leets.blog.service;

import com.leets.blog.dto.RepeatResponse;
import org.springframework.stereotype.Service;

@Service
public class StringService {
    public RepeatResponse repeat(String input) {
        return new RepeatResponse(input, input);
    }
}

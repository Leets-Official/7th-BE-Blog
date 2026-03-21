package com.example.demo.service;

import com.example.demo.dto.ResponseRepeat;
import org.springframework.stereotype.Service;

@Service
public class ServiceString {

    public ResponseRepeat repeatTwice(String text) {
        return new ResponseRepeat(text, text);
    }
}

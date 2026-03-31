package com.example.leets7th.controller;

import com.example.leets7th.dto.StringRequestDto;
import com.example.leets7th.dto.StringResponseDto;
import com.example.leets7th.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping("/string/repeat")
    public StringResponseDto repeatString(@RequestBody StringRequestDto requestDto) {
        return testService.repeatString(requestDto.getValue());
    }
}
package com.leets.blog.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.leets.blog.service.DemoService;
import com.leets.blog.dto.RepeatResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    // 서버 상태 확인 API
    @GetMapping("/health")
    public String healthCheck() {
        return "Server is Running!";
    }

    // 입력 문자열 중복 반환 API
    @PostMapping("/repeat")
    public RepeatResponse repeatString(@RequestParam String input) {
        return demoService.repeatString(input);
    }
}
package com.leets.blog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HealthController {

    // 서버 헬스체크 API
    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}
package com.example.demo.api.controller;

import com.example.demo.api.dto.RepeatResponse;
import com.example.demo.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    // 헬스체크
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    // 문자열 반복
    @PostMapping("/string/repeat")
    public RepeatResponse repeat(@RequestBody Map<String, String> request) {
        return apiService.repeat(request.get("text"));
    }
}

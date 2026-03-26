package com.example.demo1.controller;

import com.example.demo1.dto.StringRequestDto;
import com.example.demo1.dto.StringResponseDto;
import com.example.demo1.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(apiService.getHealthStatus());
    }

    @PostMapping("/string/repeat")
    public ResponseEntity<StringResponseDto> repeatString(@RequestBody StringRequestDto requestDto) {
        return ResponseEntity.ok(apiService.repeatString(requestDto));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleRuntimeException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
package com.example.leets7th.controller;

import com.example.leets7th.dto.StringRequestDto;
import com.example.leets7th.dto.StringResponseDto;
import com.example.leets7th.service.StringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    // 헬스체크
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ok");
    }

    // 문자열 반복
    @PostMapping("/repeat")
    public ResponseEntity<StringResponseDto> repeatString(
            @RequestBody StringRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                stringService.repeatString(requestDto.value())
        );
    }
}
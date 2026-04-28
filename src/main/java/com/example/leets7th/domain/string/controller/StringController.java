package com.example.leets7th.domain.string.controller;

import com.example.leets7th.domain.string.dto.StringRequestDto;
import com.example.leets7th.domain.string.dto.StringResponseDto;
import com.example.leets7th.domain.string.service.StringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "String", description = "문자열 관련 API")
@RestController
@RequestMapping("/api/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    // 헬스체크
    @Operation(summary = "헬스체크", description = "서버 상태를 확인합니다.")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ok");
    }

    // 문자열 반복
    @Operation(summary = "문자열 반복", description = "입력한 문자열을 반복 처리하여 반환합니다.")
    @PostMapping("/repeat")
    public ResponseEntity<StringResponseDto> repeatString(
            @RequestBody StringRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                stringService.repeatString(requestDto.value())
        );
    }
}

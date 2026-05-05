package com.example.demo.controller;

import com.example.demo.dto.RepeatRequest;
import com.example.demo.dto.RepeatResponse;
import com.example.demo.service.StringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "String", description = "문자열 처리 API")
@RestController
@RequestMapping("/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @Operation(summary = "문자열 2회 반환", description = "입력받은 문자열을 2개의 필드로 반복 반환합니다.")
    @PostMapping("/repeat")
    public RepeatResponse repeat(@RequestBody RepeatRequest request) {
        return stringService.repeatTwice(request.value());
    }
}

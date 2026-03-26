package com.leets.blog.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.leets.blog.service.DemoService;
import com.leets.blog.dto.RepeatRequest;
import com.leets.blog.dto.RepeatResponse;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RepeatController {
    private final DemoService demoService;

    // 입력 문자열 중복 반환 API
    @PostMapping("/string/repeat")
    public RepeatResponse repeatString(@RequestBody RepeatRequest request) {
        String result = demoService.repeatString(request.getValue());
        return new RepeatResponse(result, result);
    }
}
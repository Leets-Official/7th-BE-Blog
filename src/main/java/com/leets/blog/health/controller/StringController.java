package com.leets.blog.health.controller;


import com.leets.blog.health.dto.RepeatRequest;
import com.leets.blog.health.dto.RepeatResponse;
import com.leets.blog.health.service.StringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "string-controller", description = "문자열 처리 API")
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    @Operation(
            summary = "문자열 2번 반복",
            description = "요청 body로 받은 문자열을 2번 반복한 결과를 반환합니다."
    )
    public RepeatResponse repeatString(@RequestBody RepeatRequest request) {

        // service로
        return stringService.repeat(request.getContent());
    }
}

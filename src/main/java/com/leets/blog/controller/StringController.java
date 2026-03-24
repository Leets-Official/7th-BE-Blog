package com.leets.blog.controller;


import com.leets.blog.dto.RepeatReq;
import com.leets.blog.dto.RepeatRes;
import com.leets.blog.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    public RepeatRes repeatString(@RequestBody RepeatReq req) {
        // 서비스에서 원본 문자열 가져오기
        String original = stringService.getOriginalString(req.getContent());

        // JSON 구조로 응답
        return new RepeatRes(original, original);
    }
}

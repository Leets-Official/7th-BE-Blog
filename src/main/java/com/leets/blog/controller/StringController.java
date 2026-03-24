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
        // Service 호출
        String repeat2 = stringService.repeatString(req.getContent());

        // DTO 에 담아서 >> JSON 으로 변환
        return new RepeatRes(repeat2);
    }
}

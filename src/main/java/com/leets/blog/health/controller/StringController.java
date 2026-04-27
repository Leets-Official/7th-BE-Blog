package com.leets.blog.health.controller;


import com.leets.blog.health.dto.RepeatRequest;
import com.leets.blog.health.dto.RepeatResponse;
import com.leets.blog.health.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    public RepeatResponse repeatString(@RequestBody RepeatRequest request) {

        // service로
        return stringService.repeat(request.getContent());
    }
}

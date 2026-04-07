package com.leets.blog.domain.string.controller;

import com.leets.blog.domain.string.dto.StringRequestDto;
import com.leets.blog.domain.string.dto.StringResponseDto;
import com.leets.blog.domain.string.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/string")
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/repeat")
    public StringResponseDto repeat(@RequestBody StringRequestDto request) {

        String result = stringService.repeat(request.getValue());

        return new StringResponseDto(result, result);
    }
}
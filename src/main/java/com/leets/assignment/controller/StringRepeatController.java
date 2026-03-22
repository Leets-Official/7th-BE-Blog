package com.leets.assignment.controller;

import com.leets.assignment.dto.RepeatRequestDto;
import com.leets.assignment.dto.RepeatResponseDto;
import com.leets.assignment.service.StringRepeatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringRepeatController {

    private final StringRepeatService stringRepeatService;

    public StringRepeatController(StringRepeatService stringRepeatService) {
        this.stringRepeatService = stringRepeatService;
    }

    @PostMapping("/string/repeat")
    public RepeatResponseDto repeat(@RequestBody RepeatRequestDto requestDto) {
        // @RequestBody : 사용자가 보낸 JSON 본문을 자바 객체로 변환
        return stringRepeatService.repeatString(requestDto);
    }
}

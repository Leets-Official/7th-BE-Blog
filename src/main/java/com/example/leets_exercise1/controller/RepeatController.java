package com.example.leets_exercise1.controller;

import com.example.leets_exercise1.dto.RepeatRequest;
import com.example.leets_exercise1.dto.RepeatResponse;
import com.example.leets_exercise1.service.RepeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepeatController {
    private final RepeatService repeatService;

    public RepeatController(RepeatService repeatService) {
        this.repeatService = repeatService;
    }

    @PostMapping("/string/repeat")
    public RepeatResponse repeat(@Valid @RequestBody RepeatRequest request) {
        return repeatService.repeat(request.getValue());
    }
}
package com.example.demo.controller;

import com.example.demo.dto.RepeatRequest;
import com.example.demo.dto.RepeatResponse;
import com.example.demo.service.StringService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/repeat")
    public RepeatResponse repeat(@RequestBody RepeatRequest request) {
        return stringService.repeatTwice(request.getValue());
    }
}

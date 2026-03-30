package com.example.springbootassignment.controller;

import com.example.springbootassignment.dto.StringRequest;
import com.example.springbootassignment.dto.StringResponse;
import com.example.springbootassignment.service.StringService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/repeat")
    public StringResponse repeat(@RequestBody StringRequest request) {
        return stringService.repeatString(request.getText());
    }

}
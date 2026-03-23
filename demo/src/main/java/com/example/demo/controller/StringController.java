package com.example.demo.controller;

import com.example.demo.dto.StringRequest;
import com.example.demo.dto.StringResponse;
import com.example.demo.service.StringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StringController {

    private final StringService stringService;

    @PostMapping("/string/repeat")
    public StringResponse repeat(@RequestBody StringRequest request) {
        return stringService.repeatString(request.getText());
    }
}

package com.example.demo.controller;

import com.example.demo.dto.RequestRepeat;
import com.example.demo.dto.ResponseRepeat;
import com.example.demo.service.ServiceString;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string")
public class ControllerString {

    private final ServiceString stringService;

    public ControllerString(ServiceString stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/repeat")
    public ResponseRepeat repeat(@RequestBody RequestRepeat request) {
        return stringService.repeatTwice(request.getValue());
    }
}

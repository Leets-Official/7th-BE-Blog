package com.example.leets_week1.controller;

import com.example.leets_week1.dto.Request_dto;
import com.example.leets_week1.dto.Response_dto;
import com.example.leets_week1.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    // service 객체 선언
    final private Service stringService;

    // 생성자
    public Controller(Service stringService){
        this.stringService = stringService;
    }

    // health 요청
    @GetMapping("/health")
    public String get() {
        return "ok";
    }

    // string/repeat 요청
    @PostMapping("/string/repeat")
    // 입력; request -> 응답; response
    public Response_dto post(@RequestBody Request_dto request){
        return stringService.String(request.getValue());
    }
    }

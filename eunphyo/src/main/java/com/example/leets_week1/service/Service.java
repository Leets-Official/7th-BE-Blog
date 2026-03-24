package com.example.leets_week1.service;

import com.example.leets_week1.dto.Response_dto;

@org.springframework.stereotype.Service
public class Service {
    public Response_dto String(String input){

        return new Response_dto(input,input);
    }
}


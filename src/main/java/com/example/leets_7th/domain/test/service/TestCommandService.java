package com.example.leets_7th.domain.test.service;

import com.example.leets_7th.domain.test.dto.response.RepeatResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestCommandService {

    public RepeatResponse repeat(String value){
        return new RepeatResponse(value,value);
    }
}

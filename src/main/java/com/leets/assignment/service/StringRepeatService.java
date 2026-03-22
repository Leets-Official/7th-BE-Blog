package com.leets.assignment.service;


import com.leets.assignment.dto.RepeatRequestDto;
import com.leets.assignment.dto.RepeatResponseDto;
import org.springframework.stereotype.Service;


@Service
public class StringRepeatService {

    public RepeatResponseDto repeatString(RepeatRequestDto requestDto) {
        return new RepeatResponseDto(requestDto.getValue());
    }
}

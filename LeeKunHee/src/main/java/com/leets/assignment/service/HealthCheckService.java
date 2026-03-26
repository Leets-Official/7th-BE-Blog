package com.leets.assignment.service;

import com.leets.assignment.dto.HealthCheckResponseDto;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public HealthCheckResponseDto checkStatus() {
        return new HealthCheckResponseDto("OK");
    }
}

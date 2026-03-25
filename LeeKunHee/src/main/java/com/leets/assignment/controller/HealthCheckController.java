package com.leets.assignment.controller;

import com.leets.assignment.dto.HealthCheckResponseDto;
import com.leets.assignment.service.HealthCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthCheckService healthCheckService;


    @GetMapping("/health")
    public HealthCheckResponseDto healthCheck() {
        return healthCheckService.checkStatus();
    }
}

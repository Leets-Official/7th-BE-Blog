package com.leets.assignment.controller;

import com.leets.assignment.dto.HealthCheckResponseDto;
import com.leets.assignment.service.HealthCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/health")
    public HealthCheckResponseDto healthCheck() {
        return healthCheckService.checkStatus();
    }
}

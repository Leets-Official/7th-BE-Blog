package com.leets.blog.health.service;

import org.springframework.stereotype.Service;
@Service
public class HealthService {

    public String getHealthStatus() {
        return "OK";
    }
}

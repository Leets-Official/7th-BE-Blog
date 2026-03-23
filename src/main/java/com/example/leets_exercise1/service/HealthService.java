package com.example.leets_exercise1.service;

import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public String check() {
        return "ok";
    }
}
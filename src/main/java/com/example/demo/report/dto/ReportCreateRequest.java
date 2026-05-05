package com.example.demo.report.dto;

import lombok.Getter;

@Getter
public class ReportCreateRequest {

    private Long reporterId;

    private String reason;
}
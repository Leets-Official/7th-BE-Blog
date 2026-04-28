package com.example.springbootassignment.domain.report.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reportType;

    @Column(nullable = false)
    private Long targetId;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt;
}

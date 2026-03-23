package com.leets.blog.controller;

import com.leets.blog.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     //JSON 이나 문자열 반환하는 컨트롤러임을 선언
@RequiredArgsConstructor        // final이 붙은 필트(service)를 스프링이 자동으로 넣어줌
public class HealthController {

    private final HealthService healthService;

    @GetMapping("/health")          // get 방식으로 /health 요청시 실행
    public String healthCheck() {
        return healthService.getHealthStatus();     // service 호출
    }

}

package com.leets.blog.health.controller;

import com.leets.blog.health.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     //JSON 이나 문자열 반환하는 컨트롤러임을 선언
@RequiredArgsConstructor        // final이 붙은 필트(service)를 스프링이 자동으로 넣어줌
@Tag(name = "health-controller", description = "서버 상태 확인 API")
public class HealthController {

    private final HealthService healthService;

    @GetMapping("/health")          // get 방식으로 /health 요청시 실행
    @Operation(summary = "헬스 체크", description = "서버가 정상 동작하는지 확인합니다.")
    public String healthCheck() {
        return healthService.getHealthStatus();     // service 호출
    }

}

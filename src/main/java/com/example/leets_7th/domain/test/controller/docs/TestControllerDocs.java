package com.example.leets_7th.domain.test.controller.docs;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.domain.test.dto.request.RepeatRequest;
import com.example.leets_7th.domain.test.dto.response.RepeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Test", description = "테스트 API")
public interface TestControllerDocs {

    @GetMapping("/health")
    @Operation(summary = "헬스 체크 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "서버 정상 작동시"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류시")
    })
    ResponseEntity<ApiResponse<Void>> healthCheck();

    @PostMapping("/repeat")
    @Operation(summary = "문자열 출력 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "올바른 값 입력 시"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Null 값 입력 시")
    })
    ResponseEntity<ApiResponse<RepeatResponse>> repeat(
            @Valid @RequestBody RepeatRequest repeatRequest
    );
}

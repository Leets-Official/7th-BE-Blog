package com.example.leets_7th.domain.test.controller;

import com.example.leets_7th.common.response.ApiResponse;
import com.example.leets_7th.common.status.SuccessStatus;
import com.example.leets_7th.domain.test.controller.docs.TestControllerDocs;
import com.example.leets_7th.domain.test.dto.request.RepeatRequest;
import com.example.leets_7th.domain.test.dto.response.RepeatResponse;
import com.example.leets_7th.domain.test.service.TestCommandService;
import com.example.leets_7th.domain.test.service.TestQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/test")
@RestController
@Validated
@RequiredArgsConstructor
public class TestController implements TestControllerDocs {

    private final TestCommandService testCommandService;
    private final TestQueryService testQueryService;


    @Override
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Void>> healthCheck(){
        return ApiResponse.success(SuccessStatus.HEALTH_CHECK_SUCCESS_STATUS);
    }

    @Override
    @PostMapping("/repeat")
    public ResponseEntity<ApiResponse<RepeatResponse>> repeat(
            @Valid @RequestBody RepeatRequest repeatRequest
    ){
        RepeatResponse response = testCommandService.repeat(repeatRequest.value());
        return ApiResponse.success(SuccessStatus.STRING_REPEAT_SUCCESS,response);
    }

}

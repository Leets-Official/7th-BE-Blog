package com.example.leets7th.domain.test.controller;

import com.example.leets7th.domain.test.dto.TestResDTO;
import com.example.leets7th.domain.test.service.TestService;
import com.example.leets7th.global.apiPayload.ApiResponse;
import com.example.leets7th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class TestController {
    private final TestService testService;

    @PostMapping("/string/repeat")
    public ApiResponse<TestResDTO.TestDTO> repeat(@RequestBody String testValue) {
        TestResDTO.TestDTO result = testService.getTestValue(testValue);
        return ApiResponse.onSuccess(GeneralSuccessCode.REQUEST_OK, result);
    }
}

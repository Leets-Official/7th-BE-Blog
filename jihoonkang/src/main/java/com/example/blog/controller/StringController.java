package com.example.blog.controller;

import com.example.blog.dto.ApiResponse;
import com.example.blog.dto.StringRequest;
import com.example.blog.dto.StringResponse;
import com.example.blog.service.StringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StringController {

  private final StringService stringService;

  @PostMapping("/api/v1/strings/repeat")
  public ApiResponse<StringResponse> repeat(@RequestBody @Valid StringRequest request) {
    return ApiResponse.success(stringService.repeat(request.getValue()));
  }
}

package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StringRequest {

  @NotBlank(message = "value는 필수 입력값입니다.")
  private String value;
}

package com.example.springbootassignment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;
}

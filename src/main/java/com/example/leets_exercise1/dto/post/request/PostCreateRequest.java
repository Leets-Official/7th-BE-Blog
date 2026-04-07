package com.example.leets_exercise1.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "제목은 255자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;

    @NotBlank(message = "설명은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "설명은 255자 이하여야 합니다.")
    private String description;
}
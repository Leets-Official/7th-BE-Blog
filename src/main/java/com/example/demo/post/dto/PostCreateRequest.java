package com.example.demo.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Getter
public class PostCreateRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "설명은 비어 있을 수 없습니다.")
    private String description;

    @NotEmpty(message = "블록은 최소 1개 이상이어야 합니다.")
    private List<PostBlockDto> blocks;
}

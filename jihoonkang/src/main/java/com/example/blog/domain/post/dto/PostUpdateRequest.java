package com.example.blog.domain.post.dto;

import jakarta.validation.constraints.Size;

public record PostUpdateRequest(

    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    String title,

    String content,

    String status

) {}

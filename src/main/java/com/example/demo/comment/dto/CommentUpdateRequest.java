package com.example.demo.comment.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

@Getter
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}

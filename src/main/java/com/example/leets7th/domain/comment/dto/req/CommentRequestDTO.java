package com.example.leets7th.domain.comment.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class CommentRequestDTO {
    @Builder
    public record CreateCommentDTO(
            @NotBlank(message = "내용을 입력해주세요.")
            String content
    ) {}
}

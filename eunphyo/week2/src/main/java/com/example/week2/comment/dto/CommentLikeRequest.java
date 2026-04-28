package com.example.week2.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeRequest {

    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
}
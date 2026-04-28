package com.example.week2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReportRequest {

    @NotNull(message = "신고자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "신고 사유는 필수입니다.")
    private String reason;
}
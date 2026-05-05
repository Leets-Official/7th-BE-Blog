package com.example.leets_7th.domain.comment.dto.request;

import com.example.leets_7th.domain.comment.enums.ReportReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportCommentRequest(

        @NotNull(message = "신고 사유는 필수입니다.")
        ReportReason reason,

        @Size(max = 500, message = "신고 내용은 500자 이하로 입력해주세요.")
        String content
) {
}

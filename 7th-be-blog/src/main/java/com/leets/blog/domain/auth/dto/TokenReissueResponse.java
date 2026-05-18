package com.leets.blog.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenReissueResponse(
        @Schema(description = "새 액세스 토큰")
        String accessToken
) {
}

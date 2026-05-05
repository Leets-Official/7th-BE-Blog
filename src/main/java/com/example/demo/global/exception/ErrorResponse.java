package com.example.demo.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "공통 에러 응답 DTO")
public class ErrorResponse {

    @Schema(description = "요청 성공 여부", example = "false")
    private boolean success;

    @Schema(description = "응답 코드", example = "USER_NOT_FOUND")
    private String code;

    @Schema(description = "응답 메시지", example = "유저를 찾을 수 없습니다.")
    private String message;

    @Schema(description = "응답 데이터", example = "null")
    private Object data;
}
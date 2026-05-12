package com.leets.blog.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.leets.blog.common.exception.BaseErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@Schema(description = "공통 API 응답 래퍼")
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    @Schema(description = "요청 성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "응답 코드", example = "COMMON200")
    private final String code;

    @Schema(description = "응답 메시지", example = "요청에 성공하였습니다.")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터")
    private final T result;

    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, BaseErrorCode.SUCCESS.getCode(), BaseErrorCode.SUCCESS.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T result) {
        return new ApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, String message, T result) {
        return new ApiResponse<>(false, errorCode.getCode(), message, result);
    }
}

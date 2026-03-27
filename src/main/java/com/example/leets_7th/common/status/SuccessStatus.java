package com.example.leets_7th.common.status;

import com.example.leets_7th.common.base.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseStatus {

    // 예시
    COMM_SUCCESS_STATUS(HttpStatus.OK, "COMM_200", "성공적으로 처리되었습니다."),

    // test
    HEALTH_CHECK_SUCCESS_STATUS(HttpStatus.OK,"TEST_200","OK"),
    STRING_REPEAT_SUCCESS(HttpStatus.CREATED,"TEST_201","문자열을 성공적으로 출력했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

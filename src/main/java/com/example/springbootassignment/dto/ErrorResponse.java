package com.example.springbootassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String code;           // 에러 코드 (COMMON400, POST4041 등)
    private String message;        // 사용자 친화적인 메시지
    private String details;        // 상세 정보
}

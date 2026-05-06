package com.example.leets_exercise1.swagger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCodeExample {

    BAD_REQUEST(
            "잘못된 요청",
            "필수값 누락 또는 형식 오류",
            """
            {
              "success": false,
              "code": "4000",
              "message": "잘못된 요청입니다.",
              "result": {
                "message": "입력값을 다시 확인해주세요."
              }
            }
            """
    ),

    POST_NOT_FOUND(
            "게시글 없음",
            "존재하지 않는 게시글 ID 요청",
            """
            {
              "success": false,
              "code": "4040",
              "message": "해당 게시글을 찾을 수 없습니다.",
              "result": {
                "message": "해당 게시글을 찾을 수 없습니다."
              }
            }
            """
    ),

    COMMENT_NOT_FOUND(
            "댓글 없음",
            "존재하지 않는 댓글 ID 요청",
            """
            {
              "success": false,
              "code": "4042",
              "message": "해당 댓글을 찾을 수 없습니다.",
              "result": {
                "message": "해당 댓글을 찾을 수 없습니다."
              }
            }
            """
    ),

    REPORT_NOT_FOUND(
            "신고 없음",
            "존재하지 않는 신고 ID 요청",
            """
            {
              "success": false,
              "code": "4043",
              "message": "해당 신고를 찾을 수 없습니다.",
              "result": {
                "message": "해당 신고를 찾을 수 없습니다."
              }
            }
            """
    ),

    DUPLICATE_REPORT(
            "중복 신고",
            "같은 사용자가 동일 대상을 다시 신고",
            """
            {
              "success": false,
              "code": "4090",
              "message": "이미 신고한 대상입니다.",
              "result": {
                "message": "이미 신고한 대상입니다."
              }
            }
            """
    ),

    INTERNAL_SERVER_ERROR(
            "서버 내부 오류",
            "예상하지 못한 서버 오류",
            """
            {
              "success": false,
              "code": "5000",
              "message": "서버 내부 오류가 발생했습니다.",
              "result": {
                "message": "서버 내부 오류가 발생했습니다."
              }
            }
            """
    );

    private final String summary;
    private final String description;
    private final String body;
}
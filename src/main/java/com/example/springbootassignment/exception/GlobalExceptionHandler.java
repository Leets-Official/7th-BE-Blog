package com.example.springbootassignment.exception;

import com.example.springbootassignment.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 요청 (400 Bad Request)
     * - 제목이나 내용이 비어있는 경우
     * - 유효하지 않은 입력값
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "COMMON400",
                "잘못된 요청입니다. 입력값을 확인해주세요.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 인증 오류 (401 Unauthorized)
     * - 게시글 작성 시 필수 항목 누락
     * - JWT 토큰 없음
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "COMMON401",
                "인증이 필요합니다.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * 권한 오류 (403 Forbidden)
     * - 자신이 쓴 글이 아닌데 수정/삭제 시도
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "COMMON403",
                "접근 권한이 없습니다.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * 게시글 조회 실패 (404 Not Found)
     * - 존재하지 않는 게시글 ID로 조회/수정/삭제 시도
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "POST4041",
                "게시글을 찾을 수 없습니다.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * 댓글 조회 실패 (404 Not Found)
     * - 존재하지 않는 댓글 ID
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "COMMON404",
                "댓글을 찾을 수 없습니다.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * 내부 서버 오류 (500 Internal Server Error)
     * - 예상치 못한 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "COMMON500",
                "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

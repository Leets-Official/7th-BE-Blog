package com.example.demo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseCode {

    // 공통
    SUCCESS("2000", "성공"),
    INVALID_REQUEST("4002", "잘못된 요청입니다."),

    // user
    USER_CREATE_SUCCESS("2001", "유저 생성 성공"),

    // post
    POST_CREATE_SUCCESS("2010", "게시글 생성 성공"),
    POST_UPDATE_SUCCESS("2002", "게시글 수정 성공"),
    POST_DELETE_SUCCESS("2003", "게시글 삭제 성공"),
    POST_NOT_FOUND("4040", "해당 게시글을 찾을 수 없습니다."),

    // comment
    COMMENT_CREATE_SUCCESS("2011", "댓글 생성 성공"),
    COMMENT_UPDATE_SUCCESS("2004", "댓글 수정 성공"),
    COMMENT_DELETE_SUCCESS("2005", "댓글 삭제 성공");

    private final String code;
    private final String message;
}

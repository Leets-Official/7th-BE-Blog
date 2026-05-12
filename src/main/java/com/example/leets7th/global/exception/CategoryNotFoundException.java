package com.example.leets7th.global.exception;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(Long categoryId) {
        super(ErrorCode.CATEGORY_NOT_FOUND, ErrorCode.CATEGORY_NOT_FOUND.getMessage() + " id=" + categoryId);
    }
}

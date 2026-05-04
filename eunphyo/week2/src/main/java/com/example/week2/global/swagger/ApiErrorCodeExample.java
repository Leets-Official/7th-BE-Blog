package com.example.week2.global.swagger;

import com.example.week2.global.response.ErrorCode;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExample {
    ErrorCode[] value();
}
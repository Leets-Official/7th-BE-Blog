package com.example.leets7th.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldErrorDetail {

    private final String field;
    private final Object value;
    private final String reason;

    public FieldErrorDetail(String field, Object value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public FieldErrorDetail(String field, String reason) {
        this.field = field;
        this.value = null;
        this.reason = reason;
    }
}

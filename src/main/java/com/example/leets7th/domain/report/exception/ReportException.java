package com.example.leets7th.domain.report.exception;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;

public class ReportException extends GeneralException {
    public ReportException(BaseCode code) {
        super(code);
    }
}
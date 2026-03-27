package com.example.leets_7th.common.exception;

import com.example.leets_7th.common.base.BaseStatus;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseStatus errorStatus;

    public GeneralException(
            BaseStatus errorStatus
    ) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

}

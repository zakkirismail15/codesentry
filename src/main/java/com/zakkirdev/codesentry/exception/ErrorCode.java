package com.zakkirdev.codesentry.exception;

public enum ErrorCode {

    SYSTEM_ERROR("1001", "INTERNAL SYSTEM ERROR")

    ;


    public final String errorCode;
    public final String description;

    ErrorCode(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

}

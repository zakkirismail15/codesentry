package com.zakkirdev.codesentry.util.error;

import com.zakkirdev.codesentry.util.error.CommonError;
import com.zakkirdev.codesentry.util.error.ErrorLevels;
import com.zakkirdev.codesentry.util.error.ErrorTypes;

public enum GeneralError implements CommonError {

    SYSTEM_ERROR(ErrorLevels.ERROR, ErrorTypes.SYSTEM, "100", "Internal System Error"),
    RESOURCE_NOT_FOUND(ErrorLevels.ERROR, ErrorTypes.SYSTEM,"102", "Resource Not Found")

    ;


    private final String errorLevel;
    private final String errorType;
    private final String errorCode;
    private final String errorDesc;


    GeneralError(String errorLevel, String errorType, String errorCode, String errorDesc){
        this.errorLevel = errorLevel;
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    @Override
    public String getErrorCode() {
        return errorLevel + errorType + errorCode;
    }

    @Override
    public String getErrorLevel() {
        return errorLevel;
    }

    @Override
    public String getErrorTypes() {
        return errorType;
    }

    @Override
    public String getErrorDesc() {
        return errorDesc;
    }

    @Override
    public String getResultCode() {
        return name();
    }
}

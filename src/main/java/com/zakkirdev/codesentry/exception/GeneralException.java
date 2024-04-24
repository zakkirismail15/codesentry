package com.zakkirdev.codesentry.exception;

import com.zakkirdev.codesentry.util.error.CommonError;
import io.micrometer.common.util.StringUtils;

public class GeneralException extends RuntimeException{

    private CommonError errorCode;

    public GeneralException(CommonError errorCode){
        super(errorCode.getErrorDesc());
        this.errorCode = errorCode;
    }

    public GeneralException(CommonError errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public static GeneralException newInstance(CommonError commonError, String message){
        String errorMessage = null;
        if(StringUtils.isNotBlank(message)){
            errorMessage = commonError.getErrorDesc() + ":" + message;
        }
        return new GeneralException(commonError,errorMessage);
    }

    public CommonError getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(CommonError errorCode) {
        this.errorCode = errorCode;
    }
}

package com.zakkirdev.codesentry.controller.base;

import com.zakkirdev.codesentry.controller.response.ErrorResponse;
import com.zakkirdev.codesentry.exception.GeneralException;
import com.zakkirdev.codesentry.util.error.GeneralError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class BaseController {


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e){
        ErrorResponse errorResponse;

        if(e instanceof GeneralException exception){
            errorResponse = new ErrorResponse(exception.getErrorCode().getErrorCode(),exception.getErrorCode().getErrorDesc());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }else if( e instanceof NoSuchElementException){
            errorResponse = new ErrorResponse(GeneralError.RESOURCE_NOT_FOUND.getErrorCode(), GeneralError.RESOURCE_NOT_FOUND.getErrorDesc());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }else{
            errorResponse = new ErrorResponse(GeneralError.SYSTEM_ERROR.getErrorCode(),e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

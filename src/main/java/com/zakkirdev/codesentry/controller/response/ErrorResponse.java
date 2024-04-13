package com.zakkirdev.codesentry.controller.response;

import com.zakkirdev.codesentry.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String errorDesc;
}

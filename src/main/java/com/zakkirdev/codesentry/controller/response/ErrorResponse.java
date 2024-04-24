package com.zakkirdev.codesentry.controller.response;

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

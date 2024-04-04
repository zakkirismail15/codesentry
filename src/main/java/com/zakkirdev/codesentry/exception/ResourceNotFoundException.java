package com.zakkirdev.codesentry.exception;

public class ResourceNotFoundException extends Exception{

    private String errorMessage;
    private String description;

    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}

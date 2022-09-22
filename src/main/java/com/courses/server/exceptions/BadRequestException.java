package com.courses.server.exceptions;

public class BadRequestException extends RuntimeException {
    private int errorCode;
    public BadRequestException(int errorCode , String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

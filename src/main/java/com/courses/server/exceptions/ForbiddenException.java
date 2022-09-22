package com.courses.server.exceptions;

public class ForbiddenException extends RuntimeException {
    private int errorCode;
    public ForbiddenException(int errorCode , String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

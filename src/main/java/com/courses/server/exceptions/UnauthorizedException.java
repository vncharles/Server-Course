package com.courses.server.exceptions;

public class UnauthorizedException extends RuntimeException {
    private int errorCode;
    public UnauthorizedException(int errorCode , String message){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

package com.ssafy.exhale.exception.handler;

public class InValidParameterException extends RuntimeException {
    public InValidParameterException() {
        super();
    }

    public InValidParameterException(Throwable cause) {
        super(cause);
    }

    public InValidParameterException(String message) {
        super(message);
    }

    public InValidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}

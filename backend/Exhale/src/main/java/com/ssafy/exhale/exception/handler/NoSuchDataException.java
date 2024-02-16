package com.ssafy.exhale.exception.handler;

public class NoSuchDataException extends RuntimeException {
    public NoSuchDataException() {
        super();
    }

    public NoSuchDataException(Throwable cause) {
        super(cause);
    }

    public NoSuchDataException(String message) {
        super(message);
    }

    public NoSuchDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

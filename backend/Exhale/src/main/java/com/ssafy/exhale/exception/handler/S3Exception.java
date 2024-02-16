package com.ssafy.exhale.exception.handler;

public class S3Exception extends RuntimeException {
    public S3Exception() {
        super();
    }

    public S3Exception(Throwable cause) {
        super(cause);
    }

    public S3Exception(String message) {
        super(message);
    }

    public S3Exception(String message, Throwable cause) {
        super(message, cause);
    }
}

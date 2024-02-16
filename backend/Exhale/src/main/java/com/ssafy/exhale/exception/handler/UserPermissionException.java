package com.ssafy.exhale.exception.handler;

public class UserPermissionException extends RuntimeException {
    public UserPermissionException() {
        super();
    }

    public UserPermissionException(Throwable cause) {
        super(cause);
    }

    public UserPermissionException(String message) {
        super(message);
    }

    public UserPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}

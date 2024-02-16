package com.ssafy.exhale.exception.handler;

public class MailMessagingException extends RuntimeException {
    public MailMessagingException() {
        super();
    }

    public MailMessagingException(String message) {
        super(message);
    }

    public MailMessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailMessagingException(Throwable cause) {
        super(cause);
    }
}

package com.ssafy.exhale.exception.handler;

public class ChatGptException extends RuntimeException {
    public ChatGptException() {
        super();
    };

    public ChatGptException(Throwable cause) {
        super(cause);
    }

    public ChatGptException(String message) {
        super(message);
    }

    public ChatGptException(String message, Throwable cause) {
        super(message, cause);
    }
}

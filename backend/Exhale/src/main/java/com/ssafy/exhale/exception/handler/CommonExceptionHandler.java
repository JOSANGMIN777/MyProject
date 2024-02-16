package com.ssafy.exhale.exception.handler;

import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CommonExceptionHandler {

    //json 변환 실패 예외
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableExceptionHandle() {
        return CommonResponse.connectionError(HttpStatus.BAD_REQUEST, "invalid json format");
    }

    //get 요청 파라미터 변환 실패 예외
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchExceptionHandle() {
        return CommonResponse.connectionError(HttpStatus.BAD_REQUEST, "invalid request parameter");
    }
    
    //파라미터 검증 실패 예외
    @ExceptionHandler(InValidParameterException.class)
    public ResponseEntity<?> inValidParameterExceptionHandle(InValidParameterException exception) {
        String message = exception.getMessage();
        if(message == null) message = "invalid request parameter";
        return CommonResponse.connectionError(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> duplicateDataExceptionHandle(DuplicateDataException exception) {
        String message = exception.getMessage();
        if(message == null) message = "duplicate data exception";
        return CommonResponse.dataError(2, message);
    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<?> noSuchDataExceptionHandle(NoSuchDataException exception) {
        String message = exception.getMessage();
        if(message == null) message = "no such data exception";
        return CommonResponse.dataError(4, message);
    }

    @ExceptionHandler(ChatGptException.class)
    public ResponseEntity<?> chatGptExceptionHandle(ChatGptException exception) {
        String message = exception.getMessage();
        if(message == null) message = "server error";
        return CommonResponse.connectionError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<?> S3ExceptionHandle(S3Exception exception) {
        String message = exception.getMessage();
        if(message == null) message = "server error";
        return CommonResponse.connectionError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(UserPermissionException.class)
    public ResponseEntity<?> userPermissionExceptionHandle(UserPermissionException exception) {
        String message = exception.getMessage();
        if(message == null) message = "invalid user permission";
        return CommonResponse.connectionError(HttpStatus.UNAUTHORIZED, message);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> messagingExceptionHandle(MessagingException exception) {
        String message = exception.getMessage();
        if(message == null) message = "messaging exception";
        return CommonResponse.connectionError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}

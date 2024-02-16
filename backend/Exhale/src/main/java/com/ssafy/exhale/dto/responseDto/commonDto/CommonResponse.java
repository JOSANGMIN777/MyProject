package com.ssafy.exhale.dto.responseDto.commonDto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CommonResponse {
    private final ConnectionStatus connectionStatus;
    private final DataStatus dataStatus;
    private final Object response;

    private CommonResponse(ConnectionStatus connectionStatus, DataStatus dataStatus, Object response) {
        this.connectionStatus = connectionStatus;
        this.dataStatus = dataStatus;
        this.response = response;
    }

    public static ResponseEntity<CommonResponse> ok(Object response) {
        ConnectionStatus connectionStatus = new ConnectionStatus(HttpStatus.OK.value(), null);
        DataStatus dataStatus = new DataStatus(1, null);
        CommonResponse commonResponse = new CommonResponse(connectionStatus, dataStatus, response);
        return ResponseEntity.ok(commonResponse);
    }

    public static ResponseEntity<CommonResponse> connectionError(HttpStatus httpStatus, String message) {
        ConnectionStatus connectionStatus = new ConnectionStatus(httpStatus.value(), message);
        CommonResponse commonResponse = new CommonResponse(connectionStatus, null, null);
        return new ResponseEntity<>(commonResponse, httpStatus);
    }

    public static ResponseEntity<CommonResponse> dataError(int code, String message) {
        ConnectionStatus connectionStatus = new ConnectionStatus(HttpStatus.BAD_REQUEST.value(), null);
        DataStatus dataStatus = new DataStatus(code, message);
        CommonResponse commonResponse = new CommonResponse(connectionStatus, dataStatus, null);
        return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
    }
}

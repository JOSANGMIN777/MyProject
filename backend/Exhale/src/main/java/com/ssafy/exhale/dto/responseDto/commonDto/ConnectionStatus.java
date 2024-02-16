package com.ssafy.exhale.dto.responseDto.commonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConnectionStatus {
    private int httpCode;
    private String message;
}


package com.ssafy.exhale.dto.responseDto.commonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataStatus {
    private int code;
    private String message;
}

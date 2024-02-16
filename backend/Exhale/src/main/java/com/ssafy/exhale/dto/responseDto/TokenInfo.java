package com.ssafy.exhale.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@AllArgsConstructor
@ToString
@Getter
public class TokenInfo {
    private String access_token;
    private String refresh_token;
    private String key;
}

package com.ssafy.exhale.dto.chatGpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageRequest {
    private String role;
    private String content;
}

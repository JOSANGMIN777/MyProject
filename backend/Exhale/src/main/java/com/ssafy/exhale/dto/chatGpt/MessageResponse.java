package com.ssafy.exhale.dto.chatGpt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageResponse {
    private String role;
    private String content;
}

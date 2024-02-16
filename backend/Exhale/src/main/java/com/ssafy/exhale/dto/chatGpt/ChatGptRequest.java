package com.ssafy.exhale.dto.chatGpt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChatGptRequest {
    private String model;
    private List<MessageRequest> messages;
}

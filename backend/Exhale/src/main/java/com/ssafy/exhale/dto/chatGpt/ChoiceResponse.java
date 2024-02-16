package com.ssafy.exhale.dto.chatGpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChoiceResponse {
    private String index;
    private MessageResponse message;
    private String logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
}

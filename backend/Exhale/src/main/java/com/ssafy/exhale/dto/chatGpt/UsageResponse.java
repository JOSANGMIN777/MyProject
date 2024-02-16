package com.ssafy.exhale.dto.chatGpt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsageResponse {
    @JsonProperty("prompt_tokens")
    private int promptToken;
    @JsonProperty("completion_tokens")
    private int completionToken;
    @JsonProperty("total_tokens")
    private int totalTokens;
}

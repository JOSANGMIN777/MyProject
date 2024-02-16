package com.ssafy.exhale.dto.chatGpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ChatGptResponse {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<ChoiceResponse> choices;
    private UsageResponse usage;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
}

package com.ssafy.exhale.dto.responseDto.statisticsDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.LetterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetterDataResponse {
    private String letter;
    private long count;
    @JsonProperty("correct_count")
    private long correctCount;
    @JsonProperty("wrong_count")
    private long wrongCount;
    private LetterType type;
}

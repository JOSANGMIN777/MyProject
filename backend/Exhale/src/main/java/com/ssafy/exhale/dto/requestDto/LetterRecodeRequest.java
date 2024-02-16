package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterRecodeRequest {
    @NotNull
    @JsonProperty("letter_id")
    private Long letterId;

    @PositiveOrZero
    @NotNull
    @JsonProperty("correct_cnt")
    private Integer correctCnt;

    @PositiveOrZero
    @NotNull
    @JsonProperty("wrong_cnt")
    private Integer wrongCnt;
}

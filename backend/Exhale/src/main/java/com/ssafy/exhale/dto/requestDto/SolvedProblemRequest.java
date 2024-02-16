package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolvedProblemRequest {
    @NotNull
    @JsonProperty("problem_id")
    private Long problemId;

    @NotNull
    @JsonProperty("is_right")
    private Boolean isRight;

    @NotNull
    private Integer time;
}

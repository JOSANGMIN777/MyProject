package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProblemGetResponse {
    private List<ProblemResponse> problemResponseList;

    @JsonProperty("first_problem_index")
    private int firstProblemIndex;

    public static ProblemGetResponse from(List<ProblemResponse> problemResponseList, int firstProblemIndex) {
        return new ProblemGetResponse(problemResponseList, firstProblemIndex);
    }
}

package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.*;
import lombok.AllArgsConstructor;

public class ProblemResponse {
    @JsonProperty("problem_id")
    private long problemId;

    protected ProblemResponse(long problemId) {
        this.problemId = problemId;
    }

    public static ProblemResponse from(Problem problem) {
        if(problem instanceof NameProblem) {
            return NameProblemResponse.from((NameProblem) problem);
        }
        else if(problem instanceof ImageMatchingProblem) {
            return ImageMatchingProblemResponse.from((ImageMatchingProblem) problem);
        }
        else if(problem instanceof TextMatchingProblem) {
            return TextMatchingProblemResponse.from((TextMatchingProblem) problem);
        }
        else if(problem instanceof SpeakingProblem) {
            return SpeakingProblemResponse.from((SpeakingProblem) problem);
        }
        else if(problem instanceof FluencyProblem) {
            return FluencyProblemResponse.from((FluencyProblem) problem);
        }
        return null;
    }
}

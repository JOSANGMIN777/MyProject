package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.*;

public class ReviewProblemResponse {
    @JsonProperty("problem_id")
    private long problemId;

    @JsonProperty("category_id")
    private long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    protected ReviewProblemResponse(long problemId, long categoryId, String categoryName) {
        this.problemId = problemId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static ReviewProblemResponse from(Problem problem) {
        if(problem instanceof NameProblem) {
            return ReviewNameProblemResponse.from((NameProblem) problem);
        }
        else if(problem instanceof ImageMatchingProblem) {
            return ReviewImageMatchingProblemResponse.from((ImageMatchingProblem) problem);
        }
        else if(problem instanceof TextMatchingProblem) {
            return ReviewTextMatchingProblemResponse.from((TextMatchingProblem) problem);
        }
        else if(problem instanceof SpeakingProblem) {
            return ReviewSpeakingProblemResponse.from((SpeakingProblem) problem);
        }
        return null;
    }
}

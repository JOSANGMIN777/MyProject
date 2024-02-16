package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.ssafy.exhale.domain.rehabilitation.FluencyProblem;
import lombok.Getter;

@Getter
public class FluencyProblemResponse extends ProblemResponse {
    private final String question;

    private FluencyProblemResponse(long questionId, String question) {
        super(questionId);
        this.question = question;
    }

    public static FluencyProblemResponse from(FluencyProblem fluencyProblem) {
        return new FluencyProblemResponse(
                fluencyProblem.getId(),
                fluencyProblem.getQuestion()
        );
    }
}

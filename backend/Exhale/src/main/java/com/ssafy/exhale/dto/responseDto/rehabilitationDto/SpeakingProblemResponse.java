package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.ssafy.exhale.domain.rehabilitation.SpeakingProblem;
import lombok.Getter;

@Getter
public class SpeakingProblemResponse extends ProblemResponse {
    private final String question;

    private SpeakingProblemResponse(long problemId, String question) {
        super(problemId);
        this.question = question;
    }

    public static SpeakingProblemResponse from(SpeakingProblem speakingProblem) {
        return new SpeakingProblemResponse(
                speakingProblem.getId(),
                speakingProblem.getQuestion()
        );
    }
}

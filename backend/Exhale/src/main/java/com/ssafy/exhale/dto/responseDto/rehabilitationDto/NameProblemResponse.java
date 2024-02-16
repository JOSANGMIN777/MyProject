package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.NameProblem;
import lombok.Getter;

@Getter
public class NameProblemResponse extends ProblemResponse {
    @JsonProperty("img_url")
    private final String imgUrl;

    private final String answer;

    private final String hint;

    private NameProblemResponse(long problemId, String imgUrl, String answer, String hint) {
        super(problemId);
        this.imgUrl = imgUrl;
        this.answer = answer;
        this.hint = hint;
    }

    static public NameProblemResponse from(NameProblem nameProblem) {
        return new NameProblemResponse(
                nameProblem.getId(),
                nameProblem.getQuestionImage(),
                nameProblem.getAnswer(),
                nameProblem.getHint()
        );
    }
}

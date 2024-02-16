package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.NameProblem;
import lombok.Getter;

@Getter
public class ReviewNameProblemResponse extends ReviewProblemResponse {
    @JsonProperty("img_url")
    private final String imgUrl;

    private final String answer;

    private final String hint;

    private ReviewNameProblemResponse(long problemId, long categoryId, String categoryName, String imaUrl, String answer, String hint) {
        super(problemId, categoryId, categoryName);
        this.imgUrl = imaUrl;
        this.answer = answer;
        this.hint = hint;
    }

    public static ReviewNameProblemResponse from(NameProblem problem) {
        return new ReviewNameProblemResponse(
                problem.getId(),
                problem.getCategory().getId(),
                problem.getCategory().getCategoryName(),
                problem.getQuestionImage(),
                problem.getAnswer(),
                problem.getHint()
        );
    }
}

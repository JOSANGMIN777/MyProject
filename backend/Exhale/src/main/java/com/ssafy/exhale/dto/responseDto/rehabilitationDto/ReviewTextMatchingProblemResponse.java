package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.TextMatchingProblem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewTextMatchingProblemResponse extends ReviewProblemResponse {
    private final String question;

    private final String questionImage;

    private final List<String> options;

    private final int answer;

    private ReviewTextMatchingProblemResponse(long problemId, long categoryId, String categoryName, String question, String questionImage,
                                        List<String> options, int answer) {
        super(problemId, categoryId, categoryName);
        this.question = question;
        this.questionImage = questionImage;
        this.options = options;
        this.answer = answer;
    }

    public static ReviewTextMatchingProblemResponse from(TextMatchingProblem problem) {
        List<String> options = new ArrayList<>();
        options.add(problem.getOption1());
        options.add(problem.getOption2());
        options.add(problem.getOption3());

        return new ReviewTextMatchingProblemResponse(
                problem.getId(),
                problem.getCategory().getId(),
                problem.getCategory().getCategoryName(),
                problem.getQuestion(),
                problem.getQuestionImage(),
                options,
                problem.getAnswer()
        );
    }
}

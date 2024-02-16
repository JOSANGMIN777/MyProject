package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.ssafy.exhale.domain.rehabilitation.ImageMatchingProblem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewImageMatchingProblemResponse extends ReviewProblemResponse {
    private final String question;

    private final List<String> options;

    private final int answer;

    private ReviewImageMatchingProblemResponse(long problemId, long categoryId, String categoryName, String question, List<String> options, int answer) {
        super(problemId, categoryId, categoryName);
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public static ReviewImageMatchingProblemResponse from(ImageMatchingProblem problem) {
        List<String> options = new ArrayList<>();
        options.add(problem.getOptionImage1());
        options.add(problem.getOptionImage2());
        options.add(problem.getOptionImage3());
        options.add(problem.getOptionImage4());

        return new ReviewImageMatchingProblemResponse(
                problem.getId(),
                problem.getCategory().getId(),
                problem.getCategory().getCategoryName(),
                problem.getQuestion(),
                options,
                problem.getAnswer()
        );
    }
}

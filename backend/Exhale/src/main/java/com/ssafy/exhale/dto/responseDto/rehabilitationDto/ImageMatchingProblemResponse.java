package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.ssafy.exhale.domain.rehabilitation.ImageMatchingProblem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ImageMatchingProblemResponse extends ProblemResponse {
    private final String question;

    private final List<String> options;

    private final int answer;

    private ImageMatchingProblemResponse(long problemId, String question, List<String> options, int answer) {
        super(problemId);
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public static ImageMatchingProblemResponse from(ImageMatchingProblem imageMatchingProblem) {
        List<String> options = new ArrayList<>();
        options.add(imageMatchingProblem.getOptionImage1());
        options.add(imageMatchingProblem.getOptionImage2());
        options.add(imageMatchingProblem.getOptionImage3());
        options.add(imageMatchingProblem.getOptionImage4());

        return new ImageMatchingProblemResponse(
                imageMatchingProblem.getId(),
                imageMatchingProblem.getQuestion(),
                options,
                imageMatchingProblem.getAnswer()
        );
    }
}

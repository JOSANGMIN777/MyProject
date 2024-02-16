package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.domain.rehabilitation.TextMatchingProblem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TextMatchingProblemResponse extends ProblemResponse {
    private final String question;

    @JsonProperty("question_image")
    private final String questionImage;

    private final List<String> options;

    private final int answer;

    private TextMatchingProblemResponse(long problemId, String question, String questionImage,
                                        List<String> options, int answer) {
        super(problemId);
        this.question = question;
        this.questionImage = questionImage;
        this.options = options;
        this.answer = answer;
    }

    public static TextMatchingProblemResponse from(TextMatchingProblem textMatchingProblem) {
        List<String> options = new ArrayList<>();

        options.add(textMatchingProblem.getOption1());
        options.add(textMatchingProblem.getOption2());
        options.add(textMatchingProblem.getOption3());

        return new TextMatchingProblemResponse(
                textMatchingProblem.getId(),
                textMatchingProblem.getQuestion(),
                textMatchingProblem.getQuestionImage(),
                options,
                textMatchingProblem.getAnswer()
        );
    }
}

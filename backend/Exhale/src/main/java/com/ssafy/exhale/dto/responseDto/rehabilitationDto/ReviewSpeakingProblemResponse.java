package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import com.ssafy.exhale.domain.rehabilitation.SpeakingProblem;
import lombok.Getter;

@Getter
public class ReviewSpeakingProblemResponse extends ReviewProblemResponse {
    private final String question;

    private ReviewSpeakingProblemResponse(long problemId, long categoryId, String categoryName, String question) {
        super(problemId, categoryId, categoryName);
        this.question = question;
    }

    public static ReviewSpeakingProblemResponse from(SpeakingProblem speakingProblem) {
        return new ReviewSpeakingProblemResponse(
                speakingProblem.getId(),
                speakingProblem.getCategory().getId(),
                speakingProblem.getCategory().getCategoryName(),
                speakingProblem.getQuestion()
        );
    }
}

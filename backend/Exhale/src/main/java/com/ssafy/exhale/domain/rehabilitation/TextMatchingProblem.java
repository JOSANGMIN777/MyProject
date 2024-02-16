package com.ssafy.exhale.domain.rehabilitation;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("text_matching_problem")
@Table(name = "text_matching_problem")
public class TextMatchingProblem extends Problem {
    @Column(name = "question")
    private String question;

    @Column(name = "question_image")
    private String questionImage;

    @Column(name = "option1")
    private String option1;

    @Column(name = "option2")
    private String option2;

    @Column(name = "option3")
    private String option3;

    @Column
    private Integer answer;
}

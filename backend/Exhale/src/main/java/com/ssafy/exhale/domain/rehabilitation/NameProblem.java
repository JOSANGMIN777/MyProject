package com.ssafy.exhale.domain.rehabilitation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("name_problem")
@Table(name = "name_problem")
public class NameProblem extends Problem {
    @Column(name = "question_image")
    private String questionImage;

    @Column
    private String answer;

    @Column
    private String hint;
}

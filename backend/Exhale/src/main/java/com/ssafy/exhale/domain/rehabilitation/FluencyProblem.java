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
@DiscriminatorValue("fluency_problem")
@Table(name = "fluency_problem")
public class FluencyProblem extends Problem {
    @Column
    private String question;
}

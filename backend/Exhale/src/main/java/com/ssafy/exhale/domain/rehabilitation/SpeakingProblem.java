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
@DiscriminatorValue("speaking_problem")
@Table(name = "speaking_problem")
public class SpeakingProblem extends Problem {
    @Column
    private String question;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "is_removed")
    private Boolean isRemoved;
}

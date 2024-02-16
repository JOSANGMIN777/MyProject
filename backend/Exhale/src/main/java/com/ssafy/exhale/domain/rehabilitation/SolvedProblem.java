package com.ssafy.exhale.domain.rehabilitation;

import com.ssafy.exhale.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "solved_problem")
public class SolvedProblem {
    @Id
    @Column(name = "solved_problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "solve_time")
    private Integer solveTime;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "is_removed")
    private Boolean isRemoved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public static SolvedProblem of(boolean isCorrect, int solveTime, Member member, Problem problem) {
        return new SolvedProblem(
                null,
                isCorrect,
                solveTime,
                null,
                null,
                member,
                problem
        );
    }
}

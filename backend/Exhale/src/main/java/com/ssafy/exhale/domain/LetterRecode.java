package com.ssafy.exhale.domain;

import com.ssafy.exhale.domain.rehabilitation.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "letter_recode")
public class LetterRecode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_letter_id")
    private Long id;

    @Column(name = "correct_cnt")
    private Integer correctCnt;

    @Column(name = "wrong_cnt")
    private Integer wrongCnt;

    @Column(name = "is_removed")
    private Boolean isRemoved;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    public static LetterRecode of(int correctCnt, int wrongCnt, Member member, Letter letter) {
        return new LetterRecode(
                null,
                correctCnt,
                wrongCnt,
                null,
                null,
                member,
                letter
        );
    }
}
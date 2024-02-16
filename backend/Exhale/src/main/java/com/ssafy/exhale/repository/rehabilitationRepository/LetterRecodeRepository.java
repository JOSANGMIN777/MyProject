package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.domain.LetterRecode;
import com.ssafy.exhale.dto.responseDto.statisticsDto.LetterDataResponse;
import com.ssafy.exhale.dto.responseDto.statisticsDto.SolvedDataResponse;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SqlResultSetMapping(
        name = "CustomerDTOMapping",
        classes = @ConstructorResult(
                targetClass = SolvedDataResponse.class,
                columns = {
                        @ColumnResult(name = "co.course_id", type = String.class),
                        @ColumnResult(name = "co.course_name", type = Integer.class),
                        @ColumnResult(name = "start_of_week", type = Integer.class),
                        @ColumnResult(name = "end_of_week", type = Integer.class),
                        @ColumnResult(name = "correct_count", type = Integer.class),
                        @ColumnResult(name = "wrong_count", type = Integer.class),
                }
        )
)

public interface LetterRecodeRepository extends JpaRepository<LetterRecode ,Long> {
    @Query(value = "select new com.ssafy.exhale.dto.responseDto.statisticsDto.LetterDataResponse(lr.letter.content, sum(lr.correctCnt + lr.wrongCnt), sum(lr.correctCnt), sum(lr.wrongCnt), lr.letter.type) from LetterRecode lr " +
            "join lr.letter l " +
            "join lr.member m " +
            "where m.id = :memberId " +
            "group by lr.letter " +
            "order by sum(lr.wrongCnt) desc")
    List<LetterDataResponse> getLetterRecode(@Param("memberId") long memberId, Pageable pageable);
}

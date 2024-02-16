package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.dto.responseDto.statisticsDto.LetterDataResponse;
import com.ssafy.exhale.dto.responseDto.statisticsDto.SolvedDataResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class SolvedProblemRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<SolvedDataResponse> getSolvedData(@Param("memberId") long memberId) {
        String sql = "select " +
                "co.course_id, co.course_name, " +
                "DATE_SUB(MIN(sp.created_date), INTERVAL WEEKDAY(MIN(sp.created_date)) DAY) AS start_of_week, " +
                "DATE_ADD(DATE_SUB(MAX(sp.created_date), INTERVAL WEEKDAY(MAX(sp.created_date)) DAY), INTERVAL 6 DAY) AS end_of_week, " +
                "SUM(CASE WHEN is_correct = true THEN 1 ELSE 0 END) AS correct_count, " +
                "SUM(CASE WHEN is_correct = false THEN 1 ELSE 0 END) AS wrong_count, " +
                "SUM(solve_time) as solved_time " +
                "from solved_problem sp " +
                "join problem p on sp.problem_id = p.problem_id " +
                "join category c on p.category_id = c.category_id " +
                "join course co on c.course_id = co.course_id " +
                "join member m on m.member_id = sp.user_id " +
                "where sp.created_date >= CURDATE() - INTERVAL 1 MONTH " +
                "and sp.user_id = :memberId " +
                "group by co.course_id, YEARWEEK(sp.created_date, 1) " +
                "order by co.course_id, YEARWEEK(sp.created_date, 1) desc";

        List<Object[]> resultList = entityManager.createNativeQuery(sql).setParameter("memberId", memberId).getResultList();
        return mapToDto(resultList);
    }

    private List<SolvedDataResponse> mapToDto(List<Object[]> resultList) {
        List<SolvedDataResponse> solvedDataResponseList = new ArrayList<>();
        for (Object[] result : resultList) {
            SolvedDataResponse solvedDataResponse = new SolvedDataResponse();
            solvedDataResponse.setCourseId((Long) result[0]);
            solvedDataResponse.setCourseName((String) result[1]);
            solvedDataResponse.setStartOfWeek(((Timestamp) result[2]).toLocalDateTime());
            solvedDataResponse.setEndOfWeek(((Timestamp) result[3]).toLocalDateTime());
            solvedDataResponse.setCorrectCount(((BigDecimal) result[4]).intValue());
            solvedDataResponse.setWrongCount(((BigDecimal) result[5]).intValue());
            solvedDataResponseList.add(solvedDataResponse);
        }
        return solvedDataResponseList;
    }
}

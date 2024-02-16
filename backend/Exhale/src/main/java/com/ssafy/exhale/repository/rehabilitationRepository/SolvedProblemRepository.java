package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.domain.rehabilitation.SolvedProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, Long> {

    @Query(value = "select s.problem.id from SolvedProblem s " +
            "join s.problem p " +
            "join p.category c " +
            "where c.id = :categoryId " +
            "and s.member.loginId = :userId " +
            "order by s.createdDate desc ")
    List<Long> findLastSolvedProblem(@Param("categoryId") long categoryId, @Param("userId") String userId, Pageable pageable);
}

package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.domain.rehabilitation.Category;
import com.ssafy.exhale.domain.rehabilitation.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository<T extends Problem> extends JpaRepository<T, Long> {
    List<T> findByCategoryAndIsRemovedOrderByCreatedDate(Category category, boolean isRemoved);
    Optional<T> findByIdAndIsRemoved(long id, boolean isRemoved);

    @Query(value = "select problem from Review review " +
            "join review.problem problem " +
            "join problem.category category " +
            "join category.course course " +
            "join review.member member " +
            "where course.id = :courseId " +
            "and member.loginId = :loginId " +
            "order by review.createdDate"
    )
    List<Problem> getReviewProblem(@Param("courseId") long courseId, @Param("loginId") String loginId);
}

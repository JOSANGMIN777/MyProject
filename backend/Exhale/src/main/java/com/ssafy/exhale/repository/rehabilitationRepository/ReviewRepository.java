package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.domain.rehabilitation.Problem;
import com.ssafy.exhale.domain.rehabilitation.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMemberAndProblem(Member member, Problem problem);

    @Query(value = "select review.problem from Review review " +
            "join review.problem problem " +
            "join problem.category category " +
            "join category.course course " +
            "where course.id = :courseId " +
            "order by review.createdDate"
    )
    List<Problem> findByCourse(@Param("courseId") long courseId);
}

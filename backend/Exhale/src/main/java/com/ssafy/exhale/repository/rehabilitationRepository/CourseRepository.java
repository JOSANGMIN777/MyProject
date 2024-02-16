package com.ssafy.exhale.repository.rehabilitationRepository;

import com.ssafy.exhale.domain.rehabilitation.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByIsRemoved(Boolean isRemoved);
    Optional<Course> findByIdAndIsRemoved(Long id, Boolean isRemoved);
}

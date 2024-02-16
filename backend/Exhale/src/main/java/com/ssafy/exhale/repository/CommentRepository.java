package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.Comment;
import com.ssafy.exhale.repository.customRepository.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    List<Comment> findAllByParentCommentId(Long parentId);
}

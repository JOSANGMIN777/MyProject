package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.ArticleFile;
import com.ssafy.exhale.domain.Board;
import com.ssafy.exhale.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Override
    List<Board> findAll();
}

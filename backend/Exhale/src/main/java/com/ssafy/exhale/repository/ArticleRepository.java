package com.ssafy.exhale.repository;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.repository.customRepository.ArticleRepositoryCustom;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    List<Article> findAllByBoardIdAndIsDelete(Integer boardId, PageRequest page, Boolean isDelete);
    List<Article> findByIsDelete(PageRequest page, Boolean isDelete);
    Article findByIdAndIsDelete(Long articleId, Boolean isDelete);
    Long countBy();
    Long countByBoardId(Integer boardId);
}

package com.ssafy.exhale.repository.customRepository;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.dto.requestDto.ArticleSearchRequest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> search(ArticleSearchRequest articleSearchRequest, PageRequest pageRequest);

    List<Article> searchAll(ArticleSearchRequest articleSearchRequest, PageRequest pageRequest);

    Long countSearchedArticles(ArticleSearchRequest articleSearchRequest);
}

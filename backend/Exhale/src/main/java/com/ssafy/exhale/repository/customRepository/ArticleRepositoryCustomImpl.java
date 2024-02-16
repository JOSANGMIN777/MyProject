package com.ssafy.exhale.repository.customRepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.QArticle;
import com.ssafy.exhale.dto.requestDto.ArticleSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QArticle article = QArticle.article;
    @Override
    public List<Article> search(ArticleSearchRequest articleSearchRequest, PageRequest pageRequest) {
        JPAQuery<Article> query =  queryFactory.select(article)
                .from(article)
                .where(article.board.id.eq(articleSearchRequest.getBoardId()))
                .where(checkSearchType(articleSearchRequest.getSearchType(), articleSearchRequest.getSearchContent()))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());
        return query.fetch();
    }

    @Override
    public List<Article> searchAll(ArticleSearchRequest articleSearchRequest, PageRequest pageRequest) {
        JPAQuery<Article> query =  queryFactory.select(article)
                .from(article)
                .where(checkSearchType(articleSearchRequest.getSearchType(), articleSearchRequest.getSearchContent()))
                .offset((long) pageRequest.getPageNumber() * pageRequest.getPageSize())
                .limit(pageRequest.getPageSize());
        return query.fetch();
    }

    public Long countSearchedArticles(ArticleSearchRequest articleSearchRequest) {
        JPAQuery<Long> query =  queryFactory.select(article.count())
                .from(article)
                .where(article.board.id.eq(articleSearchRequest.getBoardId()))
                .where(checkSearchType(articleSearchRequest.getSearchType(), articleSearchRequest.getSearchContent()));
        return query.fetchOne();
    }

    private BooleanExpression checkSearchType(String searchType, String searchContent){
        if(searchType.equals("title")){
            return article.title.like("%" + searchContent + "%");
        }
        if(searchType.equals("content")){
            return article.content.like("%" + searchContent + "%");
        }
        if(searchType.equals("author")){
            return article.nickname.like("%" + searchContent + "%");
        }
        return null;
    }

}

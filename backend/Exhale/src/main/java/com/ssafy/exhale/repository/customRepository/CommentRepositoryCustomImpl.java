package com.ssafy.exhale.repository.customRepository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.exhale.domain.Comment;
import com.ssafy.exhale.domain.QComment;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    private final QComment comment = QComment.comment;


    @Override
    public List<Comment> getParentCommentListByArticleId(Long articleId) {
        JPAQuery<Comment> query = queryFactory.select(comment)
                .from(comment)
                .where(comment.parentComment.id.isNull(), comment.article.id.eq(articleId), comment.isDelete.eq(false));
        return query.fetch();
    }
}

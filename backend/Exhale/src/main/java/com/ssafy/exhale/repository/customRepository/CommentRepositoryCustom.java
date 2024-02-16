package com.ssafy.exhale.repository.customRepository;

import com.ssafy.exhale.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> getParentCommentListByArticleId(Long articleId);
}

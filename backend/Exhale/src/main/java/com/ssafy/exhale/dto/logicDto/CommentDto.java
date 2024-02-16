package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.Comment;
import com.ssafy.exhale.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    Long id;
    String content;
    Boolean isDelete;
    LocalDateTime createDate;
    LocalDateTime modifyDate;
    ArticleDto articleDto;
    MemberDto memberDto;
    CommentDto parentCommentDto;
    List<CommentDto> childCommentDtoList;

    public static CommentDto of(Long id, String content, Boolean isDelete,
                                LocalDateTime createDate, LocalDateTime modifyDate,
                                ArticleDto articleDto, MemberDto memberDto, CommentDto commentDto)
    {
        return new CommentDto(id, content, isDelete, createDate, modifyDate,
                articleDto, memberDto, commentDto, null);
    }

    public static CommentDto from(Comment entity, List<CommentDto> childCommentDtoList){
        //부모댓글이 있는 댓글 entity
        if(entity.getParentComment() != null){
            return new CommentDto(
                    entity.getId(),
                    entity.getContent(),
                    entity.getIsDelete(),
                    entity.getCreateDate(),
                    entity.getModifyDate(),
                    ArticleDto.from(entity.getArticle()),
                    MemberDto.from(entity.getMember()),
                    CommentDto.from(entity.getParentComment(), null),
                    childCommentDtoList
            );
        }
        return new CommentDto(
                entity.getId(),
                entity.getContent(),
                entity.getIsDelete(),
                entity.getCreateDate(),
                entity.getModifyDate(),
                ArticleDto.from(entity.getArticle()),
                MemberDto.from(entity.getMember()),
                null,
                childCommentDtoList
        );
    }

    public Comment toEntity(Article article, Member member, Comment parentComment){
        return Comment.of(
                id,
                content,
                isDelete,
                createDate,
                modifyDate,
                article,
                member,
                parentComment
        );
    }
}

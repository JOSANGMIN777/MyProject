package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.Board;
import com.ssafy.exhale.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDto {
    Long id;
    String title;
    String content;
    int view;
    String thumbnail;
    String nickname;
    LocalDateTime createDate;
    LocalDateTime modifyDate;
    BoardDto boardDto;
    MemberDto memberDto;
    Boolean isDelete;

    public static ArticleDto of(Long id, String title, String content, int view,
                                String thumbnail, String nickname,
                                LocalDateTime createDate, LocalDateTime modifyDate,
                                BoardDto boardDto ,MemberDto memberDto, Boolean isDelete)
    {
        return new ArticleDto(id, title, content, view,thumbnail, nickname,
                createDate, modifyDate, boardDto ,memberDto, isDelete);
    }

    //post
    public static ArticleDto of(String title, String content,
                                String thumbnail, String nickname,
                                BoardDto boardDto ,MemberDto memberDto)
    {
        return new ArticleDto(null, title, content, 0, thumbnail, nickname,
                LocalDateTime.now(), null, boardDto ,memberDto, false);
    }

    public Article toEntity(Board board, Member member){
        return Article.of(
                id,
                title,
                content,
                view,
                thumbnail,
                nickname,
                createDate,
                modifyDate,
                isDelete,
                board,
                member
        );
    }
    public static ArticleDto from(Article entity){
        return ArticleDto.of(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getView(),
                entity.getThumbnail(),
                entity.getNickname(),
                entity.getCreateDate(),
                entity.getModifyDate(),
                BoardDto.from(entity.getBoard()),
                MemberDto.from(entity.getMember()),
                entity.getIsDelete()
        );
    }
}

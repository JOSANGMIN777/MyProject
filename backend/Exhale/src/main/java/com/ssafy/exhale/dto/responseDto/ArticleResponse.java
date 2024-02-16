package com.ssafy.exhale.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.dto.logicDto.ArticleDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleResponse {
    Long id;
    String title;
    String thumbnail;
    String content;
    @JsonProperty("member_id")
    Long memberId;
    String nickname;
    int view;
    @JsonProperty("create_date")
    LocalDateTime createDate;
    @JsonProperty("modify_date")
    LocalDateTime modifyDate;
    @JsonProperty("board_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer boardId;
    @JsonProperty("board_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String board_name;


    public static ArticleResponse of(Long id, String title, String thumbnail,
                                     String content, Long memberId, String nickname, int view,
                                     LocalDateTime createDate, LocalDateTime modifyDate)
    {
        return new ArticleResponse(id, title, thumbnail, content, memberId, nickname, view, createDate, modifyDate, null, null);
    }

    public static ArticleResponse from(ArticleDto dto){
        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getThumbnail(),
                dto.getContent(),
                dto.getMemberDto().getId(),
                dto.getNickname(),
                dto.getView(),
                dto.getCreateDate(),
                dto.getModifyDate(),
                null,
                null
        );
    }

    public static ArticleResponse fromAll(ArticleDto dto){
        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getThumbnail(),
                dto.getContent(),
                dto.getMemberDto().getId(),
                dto.getNickname(),
                dto.getView(),
                dto.getCreateDate(),
                dto.getModifyDate(),
                dto.getBoardDto().toEntity().getId(),
                dto.getBoardDto().toEntity().getName()
        );
    }
}

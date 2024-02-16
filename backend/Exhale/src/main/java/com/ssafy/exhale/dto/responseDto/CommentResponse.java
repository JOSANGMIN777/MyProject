package com.ssafy.exhale.dto.responseDto;

import com.ssafy.exhale.dto.logicDto.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentResponse {
    Long id;
    String content;
    Boolean isDelete;
    LocalDateTime createDate;
    LocalDateTime modifyDate;
    String nickname;
    Long articleId;
    Long memberId;
    Long parentCommentId;
    List<CommentResponse> childCommentList;

    public static CommentResponse of(Long id, String content, Boolean isDelete,
                                     LocalDateTime createDate, LocalDateTime modifyDate,
                                     Long articleId, Long memberId, Long parentCommentId,
                                     List<CommentResponse> childCommentList, String nickname){
        return new CommentResponse(id, content, isDelete, createDate, modifyDate,
                nickname, articleId, memberId, parentCommentId, childCommentList);
    }

    public static CommentResponse from(CommentDto dto, List<CommentResponse> childCommentList){
        if(dto.getParentCommentDto() != null){
            return new CommentResponse(
                    dto.getId(),
                    dto.getContent(),
                    dto.getIsDelete(),
                    dto.getCreateDate(),
                    dto.getModifyDate(),
                    dto.getMemberDto().getNickname(),
                    dto.getArticleDto().getId(),
                    dto.getMemberDto().getId(),
                    dto.getParentCommentDto().getId(),
                    childCommentList
            );
        }
        return new CommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getIsDelete(),
                dto.getCreateDate(),
                dto.getModifyDate(),
                dto.getMemberDto().getNickname(),
                dto.getArticleDto().getId(),
                dto.getMemberDto().getId(),
                null,
                childCommentList
        );
    }
}

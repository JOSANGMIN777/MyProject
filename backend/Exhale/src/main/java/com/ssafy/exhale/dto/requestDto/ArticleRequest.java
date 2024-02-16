package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.dto.logicDto.ArticleDto;
import com.ssafy.exhale.dto.logicDto.BoardDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleRequest {
    @NotBlank
    private String title;

    @NotNull
    private String content;

    private String thumbnail;

    @NotNull
    @JsonProperty("board_id")
    private Integer boardId;

    public ArticleDto toDto(BoardDto boardDto, MemberDto memberDto){
        return ArticleDto.of(
                title,
                content,
                thumbnail,
                memberDto.getNickname(),
                boardDto,
                memberDto
        );
    }

}

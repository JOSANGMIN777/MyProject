package com.ssafy.exhale.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleListResponse {
    Integer page;
    @JsonProperty("page_size")
    Integer pageSize;
    @JsonProperty("page_total_count")
    Long pageTotalCount;
    @JsonProperty("article_total_count")
    Long articleTotalCount;
    @JsonProperty("article_list")
    List<ArticleResponse> articleList;

    public static ArticleListResponse of(
            Integer page,
            Integer pageSize,
            Long pageTotalCount,
            Long articleTotalCount,
            List<ArticleResponse> articleList){
        return new ArticleListResponse(page, pageSize, pageTotalCount, articleTotalCount, articleList);
    }
}

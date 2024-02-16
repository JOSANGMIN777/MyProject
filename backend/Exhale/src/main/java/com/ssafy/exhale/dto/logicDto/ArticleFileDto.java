package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.ArticleFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFileDto {
    private Long id;
    private ArticleDto articleDto;
    private Boolean isDelete;
    private String url;

    public static ArticleFileDto of(Long id, ArticleDto articleDto, Boolean isDelete, String url){
        return new ArticleFileDto(id, articleDto, isDelete, url);
    }

    public static ArticleFileDto from(ArticleFile entity){
        return ArticleFileDto.of(
                entity.getId(),
                ArticleDto.from(entity.getArticle()),
                entity.getIsDelete(),
                entity.getUrl()
        );
    }

    public ArticleFile toEntity(Article article){
        return ArticleFile.of(id, article, isDelete, url);
    }
}

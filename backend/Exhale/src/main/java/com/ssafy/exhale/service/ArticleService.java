package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.Board;
import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.dto.logicDto.ArticleDto;
import com.ssafy.exhale.dto.logicDto.BoardDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.requestDto.ArticleRequest;
import com.ssafy.exhale.dto.requestDto.ArticleSearchRequest;
import com.ssafy.exhale.dto.responseDto.ArticleListResponse;
import com.ssafy.exhale.dto.responseDto.ArticleResponse;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.exception.handler.UserPermissionException;
import com.ssafy.exhale.repository.ArticleRepository;
import com.ssafy.exhale.repository.BoardRepository;
import com.ssafy.exhale.repository.MemberRepository;
import com.ssafy.exhale.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final S3Util s3Util;
    private final Integer PAGE_SIZE = 10;

    public ArticleListResponse getArticleList(Integer page, Integer pageSize) {
        pageSize = pageSize == null ? PAGE_SIZE : pageSize;
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sort);
        List<Article> articleEntityList = articleRepository.findByIsDelete(pageRequest, false);
        Long articleTotalCount = articleRepository.countBy();
        Long pageTotalCount = countTotalPage(articleTotalCount, pageSize);

        List<ArticleResponse> articleList = articleEntityList.stream()
                .map(article -> {
                    ArticleDto articleDto = ArticleDto.from(article);
                    return ArticleResponse.from(articleDto);
                })
                .toList();

        return ArticleListResponse.of(page, pageSize, pageTotalCount, articleTotalCount, articleList);
    }

    public ArticleListResponse getArticleListByBoardId(Integer boardId, Integer page, Integer pageSize) {
        pageSize = pageSize == null ? PAGE_SIZE : pageSize;
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sort);
        List<Article> articleEntityList = articleRepository.findAllByBoardIdAndIsDelete(boardId, pageRequest, false);
        Long articleTotalCount = articleRepository.countByBoardId(boardId);
        Long pageTotalCount = countTotalPage(articleTotalCount, pageSize);

        List<ArticleResponse> articleList = articleEntityList.stream()
                .map(article -> {
                    ArticleDto articleDto = ArticleDto.from(article);
                    return ArticleResponse.from(articleDto);
                })
                .toList();

        return ArticleListResponse.of(page, pageSize, pageTotalCount, articleTotalCount, articleList);
    }

    public ArticleResponse getArticle(Long articleId) {
        try {
            Article article = articleRepository.findByIdAndIsDelete(articleId, false);
            article.addView();
            articleRepository.save(article);
            return ArticleResponse.from(ArticleDto.from(article));
        } catch (NullPointerException e){
            throw new NoSuchDataException(e);
        }
    }

    //request로 받도록 설정
    public void postArticle(ArticleRequest articleRequest, Long memberId) {
        try {
            Board board = boardRepository.getReferenceById(articleRequest.getBoardId());
            Member member = memberRepository.getReferenceById(memberId);

            ArticleDto articleDto = articleRequest.toDto(
                    BoardDto.from(board),
                    MemberDto.from(member)
            );

            Article article = articleDto.toEntity(
                    articleDto.getBoardDto().toEntity(),
                    articleDto.getMemberDto().toEntity()
            );

            articleRepository.save(article);
        } catch (NullPointerException e){
            throw new NoSuchDataException(e);
        }
    }

    public void modifyArticle(Long articleId, ArticleRequest articleRequest, Long memberId) {
        try{
            Article originalArticle = articleRepository.findByIdAndIsDelete(articleId, false);
            Optional<Member> memberOpt = memberRepository.findById(memberId);
            Member member = memberOpt.orElseThrow(NoSuchDataException::new);

            if(!Objects.equals(member.getId(), originalArticle.getMember().getId())){
                throw new UserPermissionException();
            }

            ArticleDto originalArticleDto = ArticleDto.from(originalArticle);

            originalArticleDto.setId(articleId);
            originalArticleDto.setModifyDate(LocalDateTime.now());
            originalArticleDto.setTitle(articleRequest.getTitle());
            originalArticleDto.setContent(articleRequest.getContent());
            originalArticleDto.setThumbnail(articleRequest.getThumbnail());

            Article modifyArticle = originalArticleDto.toEntity(
                    originalArticleDto.getBoardDto().toEntity(),
                    originalArticleDto.getMemberDto().toEntity()
            );
            articleRepository.save(modifyArticle);
        } catch (NullPointerException e) {
            throw new NoSuchDataException();
        }
    }

    public void deleteArticle(Long articleId, Long memberId){
        try{
            Article originalArticle = articleRepository.findByIdAndIsDelete(articleId,false);
            Optional<Member> memberOpt = memberRepository.findById(memberId);
            Member member = memberOpt.orElseThrow(NoSuchDataException::new);

            if(!Objects.equals(member.getId(), originalArticle.getMember().getId())){
                throw new UserPermissionException();
            }

            ArticleDto articleDto = ArticleDto.from(originalArticle);
            articleDto.setIsDelete(true);
            Article deleteArticle = articleDto.toEntity(
                    articleDto.getBoardDto().toEntity(),
                    articleDto.getMemberDto().toEntity()
            );
            articleRepository.save(deleteArticle);
        } catch (NullPointerException e){
            throw new NoSuchDataException();
        }
    }

    public ArticleListResponse search(ArticleSearchRequest searchRequest){
        try{
            Integer pageSize = searchRequest.getPageSize();
            Integer page = searchRequest.getPage();

            pageSize = pageSize != null ? pageSize : PAGE_SIZE;
            Sort sort = Sort.by("id").descending();
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, sort);
            List<Article> articleEntityList;
            if(searchRequest.getBoardId() != 0) {
                articleEntityList = articleRepository.search(searchRequest, pageRequest);
            }
            else {
                articleEntityList = articleRepository.searchAll(searchRequest, pageRequest);
            }
            Long articleTotalCount = articleRepository.countSearchedArticles(searchRequest);
            Long pageTotalCount = countTotalPage(articleTotalCount, pageSize);

            List<ArticleResponse> articleList =
                    articleEntityList.stream()
                            .map(article -> {
                                ArticleDto articleDto = ArticleDto.from(article);
                                return ArticleResponse.from(articleDto);
                            })
                            .toList();
            return ArticleListResponse.of(page, pageSize, pageTotalCount, articleTotalCount, articleList);
        } catch (NullPointerException e){
            throw new NoSuchDataException();
        }
    }

    public String saveImage(MultipartFile file) {
        return s3Util.saveImage(file);

        /*
        deprecated
        try {
            Article article = articleRepository.getReferenceById(articleId);
            articleFileRepository.save(ArticleFile.of(null, article, false, imageURL));
        } catch (NullPointerException e) {
            throw new NoSuchDataException();
        }
         */
    }

    /*
    public void deleteImage(Long articleFileId){
        try{
            ArticleFile articleFile = articleFileRepository.findById(articleFileId).get();
            ArticleFileDto articleFileDto = ArticleFileDto.from(articleFile);
            articleFileDto.setIsDelete(true);
            ArticleFile deleteArticleFile = articleFileDto.toEntity(articleFile.getArticle());
            articleFileRepository.save(deleteArticleFile);
        } catch (NullPointerException e) {
            throw new NoSuchDataException();
        }
    }
     */

    public Long countTotalPage(Long articleTotalCount, Integer pageSize){
        return (long) Math.ceil((double) articleTotalCount / pageSize);
    }
}

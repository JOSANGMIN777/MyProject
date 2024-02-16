package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.Comment;
import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.dto.logicDto.ArticleDto;
import com.ssafy.exhale.dto.logicDto.CommentDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.requestDto.CommentRequest;
import com.ssafy.exhale.dto.responseDto.CommentResponse;
import com.ssafy.exhale.exception.handler.InValidParameterException;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.exception.handler.UserPermissionException;
import com.ssafy.exhale.repository.ArticleRepository;
import com.ssafy.exhale.repository.CommentRepository;
import com.ssafy.exhale.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    public void postComment(CommentRequest commentRequest, Long memberId) {
        try {
            Article articleEntity = articleRepository.getReferenceById(commentRequest.getArticleId());
            Member memberEntity = memberRepository.getReferenceById(memberId);

            Comment commentEntity = null;
            if (commentRequest.getParentId() != null) {
                commentEntity = commentRepository.getReferenceById(commentRequest.getParentId());
                System.out.println("commentEntity.getArticle().getId() = " + commentEntity.getArticle().getId());
                System.out.println("commentRequest.getArticleId() = " + commentRequest.getArticleId());
                if(!Objects.equals(commentEntity.getArticle().getId(), commentRequest.getArticleId())){
                    throw new InValidParameterException("현재 게시글에 존재하지 않는 댓글에 대댓글을 작성했습니다.");
                }
            }

            CommentDto commentDto = commentRequest.toDto(
                    ArticleDto.from(articleEntity),
                    MemberDto.from(memberEntity),
                    null
            );
            commentRepository.save(commentDto.toEntity(articleEntity, memberEntity, commentEntity));
        } catch (DataIntegrityViolationException exception){
            throw new InValidParameterException("상위 댓글이 없습니다.");
        } catch (EntityNotFoundException exception) {
            throw new InValidParameterException("현재 게시글에 존재하지 않는 댓글에 대댓글을 작성했습니다.");
        }
    }

    @Transactional
    public List<CommentResponse> getCommentListByArticleId(Long articleId){
        try {
            List<Comment> commentEntityList = commentRepository.getParentCommentListByArticleId(articleId);

            List<CommentDto> commentDtoList = commentEntityList.stream()
                    .map(comment -> {
                        List<CommentDto> childDtoList = comment.getChildCommentList().stream()
                                .map(child -> CommentDto.from(child, null))
                                .toList();

                        return CommentDto.from(comment, childDtoList);
                    })
                    .toList();

            return commentDtoList.stream()
                    .map(commentDto -> {
                        List<CommentResponse> childResponseList = commentDto.getChildCommentDtoList().stream()
                                .map(childDto -> CommentResponse.from(childDto, null))
                                .toList();

                        return CommentResponse.from(commentDto, childResponseList);
                    })
                    .toList();
        }catch (Exception e){
            System.out.println("error");
            return null;
        }
    }

    public void modifyComment(Long commentId, String content, Long memberId){
        try{
            Comment comment = commentRepository.findById(commentId).get();
            if(!memberId.equals(comment.getMember().getId())){
                throw new UserPermissionException("수정 권한이 없습니다.");
            }
            CommentDto commentDto = CommentDto.from(comment, null);
            commentDto.setContent(content);

            Article article = articleRepository.getReferenceById(commentDto.getArticleDto().getId());
            Member member = memberRepository.getReferenceById(commentDto.getMemberDto().getId());
            Comment modifyComment = commentDto.toEntity(article, member, null);
            commentRepository.save(modifyComment);
        }catch (Exception e){
            throw new NoSuchDataException(e);
        }
    }

    public void deleteComment(Long commentId, Long memberId) {
        try {
            Comment comment = commentRepository.findById(commentId).get();
            if(!memberId.equals(comment.getMember().getId())){
                throw new UserPermissionException("삭제 권한이 없습니다.");
            }
            CommentDto commentDto = CommentDto.from(comment, null);
            commentDto.setIsDelete(true);

            Article article = articleRepository.getReferenceById(commentDto.getArticleDto().getId());
            Member member = memberRepository.getReferenceById(commentDto.getMemberDto().getId());
            Comment modifyComment = commentDto.toEntity(article, member, null);
            commentRepository.save(modifyComment);
        } catch (Exception e){
           throw new NoSuchDataException(e);
        }
    }
}

package com.ssafy.exhale.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "comment_id")
    private Comment parentComment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> childCommentList;

    public static Comment of(Long id, String content, Boolean isDelete,
                             LocalDateTime createDate, LocalDateTime modifyDate,
                             Article article, Member member,
                             Comment parentComment, List<Comment> childCommentList)
    {
        return new Comment(id, content, isDelete, createDate, modifyDate,
                article, member, parentComment, childCommentList);
    }

    public static Comment of(Long id, String content, Boolean isDelete,
                             LocalDateTime createDate, LocalDateTime modifyDate,
                             Article article, Member member, Comment parentComment)
    {
        return new Comment(id, content, isDelete, createDate, modifyDate,
                article, member, parentComment, null);
    }

}

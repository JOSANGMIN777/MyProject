package com.ssafy.exhale.domain;

import com.ssafy.exhale.dto.requestDto.MemberRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor()
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "login_id")
    private String loginId;
    private String password;
    private String name;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "email_domain")
    private String emailDomain;
    private String birth;
    private String nickname;
    @Column(name = "withdraw", columnDefinition = "TINYINT(1) default 0")
    private Boolean withdraw;
    @Column(name = "refresh_value", nullable = true)
    private String refreshValue;
    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "withdraw_date", nullable = true)
    private LocalDateTime withdrawDate;
    private String role;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfileImage profileImage;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
    }
    public static Member of(){
        return new Member();
    }
    public static Member of(Long id, String loginId, String password,
                            String name, String emailId, String emailDomain,
                            String birth, String nickname, Boolean withhdraw,
                            LocalDateTime createDate, LocalDateTime withdrawDate, String role, ProfileImage profileImage){
        return new Member(id, loginId, password, name, emailId, emailDomain,
                birth, nickname, withhdraw, null, createDate, withdrawDate, role, profileImage);
    }

}


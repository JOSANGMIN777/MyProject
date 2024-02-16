package com.ssafy.exhale.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "profile_image")
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_image_id")
    private Long id;
    private String image;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }

    public static ProfileImage of(){
        return new ProfileImage();
    }
    public static ProfileImage of(Long id, String image, LocalDateTime createAt ,Member member){
        return new ProfileImage(id, image, createAt ,member);
    }
}

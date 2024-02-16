package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.domain.ProfileImage;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImageDto {
    private Long id;
    private String image;
    private LocalDateTime createAt;
    private Member member;

    public static ProfileImageDto of(Long id, String image, LocalDateTime createAt, Member member){
        return new ProfileImageDto(id, image, createAt ,member);
    }

    public static ProfileImageDto from(ProfileImage entity){
        return new ProfileImageDto(
                entity.getId(),
                entity.getImage(),
                entity.getCreateAt(),
                entity.getMember()
                );
    }
    public ProfileImage toEntity(){
        return ProfileImage.of(id, image, createAt, member);
    }
}




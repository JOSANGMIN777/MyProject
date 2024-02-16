package com.ssafy.exhale.dto.responseDto;

import com.ssafy.exhale.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberProfileResponse {
    private Long id;
    private String loginId;
    private String name;
    private String emailId;
    private String emailDomain;
    private String birth;
    private String nickname;
    private String image_url;

    public static MemberProfileResponse from(Member member) {
        return new MemberProfileResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getEmailId(),
                member.getEmailDomain(),
                member.getBirth(),
                member.getNickname(),
                member.getProfileImage().getImage()
        );
    }
}

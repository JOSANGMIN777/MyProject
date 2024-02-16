package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.domain.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String emailId;
    private String emailDomain;
    private String birth;
    private String nickname;
    private Boolean withdraw;
    private LocalDateTime createDate;
    private LocalDateTime withdrawDate;
    private String role;
    private ProfileImageDto profileImageDto;

    public static MemberDto of(){
        return new MemberDto();
    }
    public static MemberDto of(Long id, String loginId, String password,
                               String name, String emailId, String emailDomain,
                               String birth, String nickname, Boolean withdraw,
                               LocalDateTime createDate, LocalDateTime withdrawDate, String role, ProfileImageDto profileImageDto)
    {
        return new MemberDto(
                id,
                loginId,
                password,
                name,
                emailId,
                emailDomain,
                birth,
                nickname,
                withdraw,
                createDate,
                withdrawDate,
                role,
                profileImageDto
        );
    }
    public static MemberDto from(Member entity){
        return new MemberDto(
                entity.getId(),
                entity.getLoginId(),
                entity.getPassword(),
                entity.getName(),
                entity.getEmailId(),
                entity.getEmailDomain(),
                entity.getBirth(),
                entity.getNickname(),
                entity.getWithdraw(),
                entity.getCreateDate(),
                entity.getWithdrawDate(),
                entity.getRole(),
                ProfileImageDto.from(entity.getProfileImage())
        );
    }
    public Member toEntity(){
        return Member.of(
                id,
                loginId,
                password,
                name,
                emailId,
                emailDomain,
                birth,
                nickname,
                withdraw,
                createDate,
                withdrawDate,
                role,
                profileImageDto!=null?profileImageDto.toEntity(): null
        );
    }


}

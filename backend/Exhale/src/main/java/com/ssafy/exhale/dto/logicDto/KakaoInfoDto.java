package com.ssafy.exhale.dto.logicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoInfoDto {
    String nickname;
    String emailId;
    String emailDomain;

    public static KakaoInfoDto of(String nickname, String emailId, String emailDomain){
        return new KakaoInfoDto(nickname, emailId, emailDomain);
    }

    public MemberDto toDto(){
        return MemberDto.of(
                null,
                null,
                null,
                null,
                emailId,
                emailDomain,
                null,
                nickname,
                null,
                null,
                null,
                null,
                null
        );
    }
}

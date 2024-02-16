package com.ssafy.exhale.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class MemberResponse {
    private Long member_id;
    private String nickname;
    private String login_id;
}

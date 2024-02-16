package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRequest {
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{4,}$")
    @JsonProperty("login_id")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_+=<>?]).{10,}$")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @JsonProperty("email_id")
    private String emailId;

    @NotBlank
    @JsonProperty("email_domain")
    private String emailDomain;

    @NotBlank
    private String birth;

    @NotBlank
    private String nickname;

    public static MemberRequest of(){
        return new MemberRequest();
    }
    public static MemberRequest of(String loginId, String password, String name, String emailId, String emailDomain, String birth, String nickname){
        return new MemberRequest(
                loginId,
                password,
                name,
                emailId,
                emailDomain,
                birth,
                nickname
        );
    }

    public MemberDto toDto(){
        return MemberDto.of(
                null,
                loginId,
                password,
                name,
                emailId,
                emailDomain,
                birth,
                nickname,
                null,
                null,
                null,
                null,
                null
        );
    }
}

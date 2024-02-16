package com.ssafy.exhale.dto.logicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthAttributesDto {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    String loginId;
    String password;
    String name;
    String emailId;
    String emailDomain;
    String birth;
    String nickname;

    public static OauthAttributesDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        if("naver".equals(registrationId)) {
//            return ofNaver("id", attributes);
//        } else if ("google".equals(registrationId)) {
//            return ofGoogle(userNameAttributeName, attributes);
//        }
        return ofKakao("id", attributes);
    }

    private static OauthAttributesDto ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println(attributes.toString());
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) attributes.get("profile");
        String email = (String) response.get("email");
        String[] emailIdDomain = email.split("@");
        return new OauthAttributesDto(
                response,
                userNameAttributeName,
                UUID.randomUUID() + (String) account.get("sub") + "ID",
                UUID.randomUUID() + (String) account.get("sub") + "PWD",
                (String) account.get("name"),
                emailIdDomain[0],
                emailIdDomain[1],
                (String) account.get("nickname"),
                (String) account.get("birthdate")
        );
    }
    public MemberDto toDto() {
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

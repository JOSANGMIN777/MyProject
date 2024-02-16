package com.ssafy.exhale.controller;

import com.ssafy.exhale.dto.logicDto.AuthenticationDto;
import com.ssafy.exhale.dto.logicDto.KakaoInfoDto;
import com.ssafy.exhale.dto.logicDto.KakaoTokenDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.responseDto.MemberResponse;
import com.ssafy.exhale.dto.responseDto.TokenInfo;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.service.AuthenticationService;
import com.ssafy.exhale.service.OauthService;
import com.ssafy.exhale.util.GenerateRandomKey;
import com.ssafy.exhale.util.TokenPayloadUtil;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class SocialController {
    private final OauthService oauthService;
    private final TokenPayloadUtil tokenPayloadUtil;
    private final AuthenticationService authenticationService;
    @GetMapping("/kakao/login")
    public ResponseEntity<?> kakoAuthLogin(@RequestParam String code) throws ParseException {
        KakaoTokenDto kakaoTokenResponse = oauthService.requestKakaoToken(code, "login").getBody();
        KakaoInfoDto kakaoInfoDto = oauthService.requestKakaoUserInfo(kakaoTokenResponse);
        MemberDto memberDto = oauthService.findUserByEmail(kakaoInfoDto);
        if(memberDto == null) {
            return CommonResponse.dataError(4, "해당 회원 정보 없음");
        }
        if(memberDto.getWithdraw()){
            return CommonResponse.dataError(5, "탈퇴 회원");
        }

        Long memberId = memberDto.getId();
        String nickname = memberDto.getNickname();
        String loginId = memberDto.getLoginId();

        String jwt = tokenPayloadUtil.createJWT();
        String refreshToken = tokenPayloadUtil.createRefreshToken();

        String key = GenerateRandomKey.getRandomKey(memberId);
        AuthenticationDto authenticationDto = AuthenticationDto.of();
        authenticationDto.setMemberId(memberId);
        authenticationDto.setRefreshValue(refreshToken);
        authenticationDto.setKey(key);
        authenticationService.saveRefreshValue(authenticationDto);

        TokenInfo tokeninfo = new TokenInfo("Bearer " + jwt, "Bearer " + refreshToken, key);
        MemberResponse memberResponse = new MemberResponse(memberId,nickname,loginId);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token",tokeninfo);
        responseBody.put("member",memberResponse);

        return CommonResponse.ok(responseBody);
    }

    @GetMapping("/kakao/join")
    public ResponseEntity<?> kakoAuthRequest(@RequestParam String code) throws ParseException {
        KakaoTokenDto kakaoTokenResponse = oauthService.requestKakaoToken(code, "signup").getBody();
        KakaoInfoDto kakaoInfoDto = oauthService.requestKakaoUserInfo(kakaoTokenResponse);
        if(oauthService.findUserByEmail(kakaoInfoDto) != null){
            return CommonResponse.dataError(2, "이미 존재하는 회원 이메일");
        }
        return CommonResponse.ok(kakaoInfoDto);
    }
}

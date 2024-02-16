package com.ssafy.exhale.controller;

import com.ssafy.exhale.dto.logicDto.AuthenticationDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.requestDto.EmailRequest;
import com.ssafy.exhale.dto.requestDto.MemberRequest;
import com.ssafy.exhale.dto.responseDto.MemberResponse;
import com.ssafy.exhale.dto.responseDto.TokenInfo;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.dto.responseDto.commonDto.ConnectionStatus;
import com.ssafy.exhale.exception.handler.InValidParameterException;
import com.ssafy.exhale.service.AuthenticationService;
import com.ssafy.exhale.service.MemberService;
import com.ssafy.exhale.util.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/general")
public class GeneralController {
    private final MemberService memberService;
    private final TokenPayloadUtil tokenPayloadUtil;
    private final AuthenticationService authenticationService;
    private final EmailUtil emailUtil;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        memberService.join(memberRequest.toDto());
        return CommonResponse.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        long memberId = tokenPayloadUtil.getMemberId();
        String nickname = tokenPayloadUtil.getNickname();
        String loginId = tokenPayloadUtil.getLoginId();

        if(memberService.checkWithdraw(memberId)) {
            return CommonResponse.dataError(5, "회원 탈퇴된 유저");
        }

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

    @PostMapping("/id")
    public ResponseEntity<?> checkLoginId(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors("loginId")) {
            throw new InValidParameterException();
        }

        if(memberService.checkLoginId(memberRequest.getLoginId())) return CommonResponse.dataError(5, "이미 존재하는 ID");
        return CommonResponse.ok(null);
    }
}

package com.ssafy.exhale.controller;

import com.ssafy.exhale.dto.logicDto.AuthenticationDto;
import com.ssafy.exhale.dto.requestDto.KeyRequest;
import com.ssafy.exhale.dto.requestDto.MemberRequest;
import com.ssafy.exhale.dto.requestDto.NicknameRequest;
import com.ssafy.exhale.dto.requestDto.PasswordRequest;
import com.ssafy.exhale.dto.responseDto.TokenInfo;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.exception.handler.InValidParameterException;
import com.ssafy.exhale.service.AuthenticationService;
import com.ssafy.exhale.service.MemberService;
import com.ssafy.exhale.util.EmailUtil;
import com.ssafy.exhale.util.GenerateRandomKey;
import com.ssafy.exhale.util.TokenPayloadUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenPayloadUtil tokenPayloadUtil;
    private final AuthenticationService authenticationService;
    private final EmailUtil emailUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> getRefreshToken(HttpServletRequest request, @Validated @RequestBody KeyRequest keyRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        // DB와 토큰 비교
        String token_value = request.getHeader("Authorization").split(" ")[1];
        long memberId = tokenPayloadUtil.getMemberId();
        Map<String, Object> responseBody = new HashMap<>();
        if (authenticationService.compareRefreshToken(memberId, token_value, keyRequest.getKey())) {
            // 새로운 토큰 발행
            String jwt = tokenPayloadUtil.createJWT();
            String refreshToken = tokenPayloadUtil.createRefreshToken();

            AuthenticationDto authenticationDto = AuthenticationDto.of();
            authenticationDto.setMemberId(memberId);
            authenticationDto.setRefreshValue(refreshToken);
            authenticationDto.setKey(keyRequest.getKey());
            authenticationService.reSaveRefreshValue(authenticationDto);

            String key = GenerateRandomKey.getRandomKey(memberId);

            TokenInfo tokeninfo = new TokenInfo("Bearer " + jwt, "Bearer " + refreshToken, key);

            responseBody.put("token", tokeninfo);

        } else {
            return CommonResponse.connectionError(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
        return CommonResponse.ok(responseBody);
    }

    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors("password")) throw new InValidParameterException();

        if(!memberService.checkPassword(tokenPayloadUtil.getMemberId(), memberRequest.getPassword())) return CommonResponse.dataError(6, "not match password");
        return CommonResponse.ok(null);
    }

    @PostMapping("/repassword")
    public ResponseEntity<?> changePassword(@Validated @RequestBody PasswordRequest passwordRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new InValidParameterException();

        if(!memberService.changePassword(tokenPayloadUtil.getMemberId(), passwordRequest.getOldPassword(), passwordRequest.getNewPassword())) {
            return CommonResponse.connectionError(HttpStatus.BAD_REQUEST, "not match password");
        }
        return CommonResponse.ok(null);
    }

    @GetMapping("/withdraw")
    public ResponseEntity<?> withdrawal(){
        memberService.changeWithdraw(tokenPayloadUtil.getMemberId());
        return CommonResponse.ok(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody KeyRequest keyRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }
        authenticationService.logout(tokenPayloadUtil.getMemberId(), keyRequest.getKey());
        return CommonResponse.ok(null);
    }

    @PostMapping("/nickname")
    public ResponseEntity<?> setNickname(@Validated @RequestBody NicknameRequest nicknameRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        Long memberId = tokenPayloadUtil.getMemberId();
        memberService.setNickname(memberId, nicknameRequest);
        return CommonResponse.ok(null);
    }

    @PostMapping("/profile-image")
    public ResponseEntity<?> setProfileImage(@RequestParam("image") MultipartFile image){
        Long memberId = tokenPayloadUtil.getMemberId();
        memberService.setProfileImage(image, memberId);
        return CommonResponse.ok(null);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return CommonResponse.ok(memberService.getProfile(tokenPayloadUtil.getMemberId()));
    }
}

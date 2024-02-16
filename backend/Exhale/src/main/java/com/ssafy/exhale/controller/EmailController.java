package com.ssafy.exhale.controller;

import com.ssafy.exhale.domain.CertificationCode;
import com.ssafy.exhale.dto.logicDto.CertificationCodeDto;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.requestDto.CertificationCodeRequest;
import com.ssafy.exhale.dto.requestDto.EmailRequest;
import com.ssafy.exhale.dto.requestDto.MemberRequest;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.exception.handler.DuplicateDataException;
import com.ssafy.exhale.exception.handler.InValidParameterException;
import com.ssafy.exhale.exception.handler.MailMessagingException;
import com.ssafy.exhale.service.CertificationCodeService;
import com.ssafy.exhale.service.MemberService;
import com.ssafy.exhale.util.EmailUtil;
import com.ssafy.exhale.util.GenerateCertificationCode;
import com.ssafy.exhale.util.TokenPayloadUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final MemberService memberService;
    private final EmailUtil emailUtil;
    private final CertificationCodeService certificationCodeService;

    @PostMapping("/certification")
    public ResponseEntity<?> certificationEmail(@Validated @RequestBody EmailRequest emailRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        if(!memberService.checkEmail(emailRequest)) {
            String fullEmail = emailRequest.getEmailId() + "@" + emailRequest.getEmailDomain();
            String certificationNumber = GenerateCertificationCode.getCertificationNumber();
            certificationCodeService.saveCode(CertificationCodeDto.of(null, fullEmail, certificationNumber, null));
            try {
                emailUtil.sendCertificationMail(fullEmail, certificationNumber);
            } catch (MessagingException e) {
                throw new MailMessagingException(e);  // todo 예외처리 수정
            }
        } else {
            throw new DuplicateDataException();
        }

        return CommonResponse.ok(null);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkCertificationCode(@Validated @RequestBody CertificationCodeRequest certificationCodeRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new InValidParameterException();
        }

        String code = certificationCodeRequest.getCode();
        if(code == null) {
            throw new InValidParameterException();
        }

        boolean isSuccess = certificationCodeService.compareCode(certificationCodeRequest);
        if(isSuccess) return CommonResponse.ok(null);
        else return CommonResponse.connectionError(HttpStatus.BAD_REQUEST, "not match code");
    }

    @PostMapping("/temp-password")
    public ResponseEntity<?> tempPassword(@Validated @RequestBody MemberRequest memberRequest, BindingResult bindingResult) {

        if(bindingResult.hasFieldErrors("emailId") || bindingResult.hasFieldErrors("emailDomain")) {
            throw new InValidParameterException();
        }

        MemberDto memberDto = memberService.checkLoginIdEmail(memberRequest);
        String fullEmail = memberDto.getEmailId()+ "@" +memberDto.getEmailDomain();
        String tempPassword = GenerateCertificationCode.getRandomPassword();
        memberService.saveTempPassword(memberDto.getId(), tempPassword);
        try {
            emailUtil.sendTempPasswordMail(fullEmail, tempPassword);
        } catch (MessagingException e) {
            throw new MailMessagingException(e);
        }
        return ResponseEntity.ok(null);
    }
}

package com.ssafy.exhale.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;

    public void sendCertificationMail(String email, String certificationCode) throws MessagingException {
        String subject = "[날: 숨] 인증 메일 입니다";
        this.sendMail(email, subject, getCertificationMessage(certificationCode));
    }
    public void sendTempPasswordMail(String email, String certificationCode) throws MessagingException {
        String subject = "[날: 숨] 임시 비밀번호 발송 메일 입니다";
        this.sendMail(email, subject, getTempPasswordMessage(certificationCode));
    }
    private void sendMail(String email, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(htmlContent, true);

        javaMailSender.send(message);
    }

    private  String getCertificationMessage(String certificationCode){
        String content = "";
        content += "<h2 style='text-align:center;'>아이디 찾기를 위한 인증코드입니다.<br/>아래 번호를 [날: 숨] 웹 페이지에 입력해주세요.</h2>";
        content += "<h3 style='text-align:center;'>인증코드는 <strong style='font-size:32px;letter-spacing: 8px'>" + certificationCode + "</strong>입니다.</h3>";
        return content;
    }
    private  String getTempPasswordMessage(String certificationCode){
        String content = "";
        content += "<h2 style='text-align:center;'>아래 코드는 임시 비밀 번호입니다.<br/>로그인 후 [날: 숨] 마이페이지에서 비밀번호를 수정하세요.</h2>";
        content += "<h3 style='text-align:center;'>임시 비밀번호는 <strong style='font-size:32px;letter-spacing: 8px'>" + certificationCode + "</strong>입니다.</h3>";
        return content;
    }
}

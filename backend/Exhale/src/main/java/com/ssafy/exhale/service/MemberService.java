package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Article;
import com.ssafy.exhale.domain.ArticleFile;
import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.domain.ProfileImage;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.dto.logicDto.ProfileImageDto;
import com.ssafy.exhale.dto.requestDto.EmailRequest;
import com.ssafy.exhale.dto.requestDto.MemberRequest;
import com.ssafy.exhale.dto.requestDto.NicknameRequest;
import com.ssafy.exhale.dto.responseDto.MemberProfileResponse;
import com.ssafy.exhale.exception.handler.DuplicateDataException;
import com.ssafy.exhale.exception.handler.NoSuchDataException;
import com.ssafy.exhale.repository.MemberRepository;
import com.ssafy.exhale.repository.ProfileImageRepository;
import com.ssafy.exhale.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3Util s3Util;
    private final ProfileImageRepository profileImageRepository;

    @Value("${profile.image.url.default}")
    private String defaultImage;

    @Transactional
    public void join(MemberDto memberDto) {
        String loginId = memberDto.getLoginId();
        String password = memberDto.getPassword();

        Boolean isExist = memberRepository.existsByLoginId(loginId);
        if (isExist) {
             throw new DuplicateDataException("회원 탈퇴된 회원이거나, 이미 존재하는 회원입니다.");
        }
        memberDto.setRole("ROLE_USER");
        memberDto.setPassword(bCryptPasswordEncoder.encode(password));
        memberDto.setWithdraw(false);
        Member member = memberDto.toEntity();
        memberRepository.save(member);

        ProfileImageDto profileImageDto = ProfileImageDto.of(null, defaultImage, null, member);
        profileImageRepository.save(profileImageDto.toEntity());
    }
    
    public boolean checkLoginId(String loginId){
        return memberRepository.existsByLoginIdAndWithdrawFalse(loginId);
    }

    public boolean verifyPassword(Long id, String newPassword){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
        return passwordEncoder.matches(newPassword, member.getPassword());
    }

    public boolean checkPassword(Long id, String newPassword){
        return verifyPassword(id, newPassword);
    }

    public boolean changePassword(Long id, String currentPassword, String newPassword) {
        if (verifyPassword(id, currentPassword)) {
            Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
            MemberDto memberDto = MemberDto.from(member);
            memberDto.setPassword(bCryptPasswordEncoder.encode(newPassword));
            memberRepository.save(memberDto.toEntity());
            return true;
        }
        return false;
    }

    public void changeWithdraw(Long id){
        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
        MemberDto memberDto = MemberDto.from(member);
        memberDto.setWithdraw(true);
        memberRepository.save(memberDto.toEntity());
    }

    public boolean checkWithdraw(Long id){
        AtomicReference<Boolean> isWithdraw = new AtomicReference<>(false);
        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
        isWithdraw.set(member.getWithdraw());
        return isWithdraw.get();
    }
    
    public boolean compareEmail(Long id, EmailRequest emailRequest){
        String newEmailId = emailRequest.getEmailId();
        String newEmailDomain = emailRequest.getEmailDomain();
        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);

        String emailId = member.getEmailId();
        String emailDomain = member.getEmailDomain();
        return (newEmailId.equals(emailId) && newEmailDomain.equals(emailDomain));
    }

    public boolean checkEmail(EmailRequest emailRequest){
        String emailId = emailRequest.getEmailId();
        String emailDomain = emailRequest.getEmailDomain();
        return memberRepository.existsByEmailIdAndEmailDomain(emailId, emailDomain);
    }

    public void setNickname(Long id, NicknameRequest nicknameRequest) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
        MemberDto memberDto = MemberDto.from(member);
        memberDto.setNickname(nicknameRequest.getNickname());
        memberRepository.save(memberDto.toEntity());
    }

    public MemberDto checkLoginIdEmail(MemberRequest memberRequest){
        String loginId = memberRequest.getLoginId();
        String emailId = memberRequest.getEmailId();
        String emailDomain = memberRequest.getEmailDomain();
        Member member = memberRepository.findByLoginIdAndEmailIdAndEmailDomainAndWithdrawFalse(loginId,emailId,emailDomain)
                .orElseThrow(NoSuchDataException::new);
        return MemberDto.from(member);
    }

    public void saveTempPassword(Long id, String password){
        Member member = memberRepository.findById(id).orElseThrow(NoSuchDataException::new);
        MemberDto memberDto = MemberDto.from(member);
        memberDto.setPassword(bCryptPasswordEncoder.encode(password));
        memberRepository.save(memberDto.toEntity());
    }

    @Transactional
    public void setProfileImage(MultipartFile file, Long id){
        String imageURL = s3Util.saveImage(file);

        memberRepository.findById(id).ifPresentOrElse((member) -> {
            ProfileImageDto profileImageDto = ProfileImageDto.from(member.getProfileImage());
            profileImageDto.setImage(imageURL);
            profileImageRepository.save(profileImageDto.toEntity());
        },()->{
            throw new NoSuchDataException();
        });
    }

    public MemberProfileResponse getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchDataException::new);
        return MemberProfileResponse.from(member);
    }
}

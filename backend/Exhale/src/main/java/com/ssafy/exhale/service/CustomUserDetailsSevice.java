package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.dto.logicDto.CustomUserDetails;
import com.ssafy.exhale.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsSevice implements UserDetailsService {
    private final MemberRepository memberRepository;
    public CustomUserDetailsSevice(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String login_id) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginIdAndWithdrawFalse(login_id);

        if(member!=null){
            return new CustomUserDetails(member);
        }

        return null;
    }
}

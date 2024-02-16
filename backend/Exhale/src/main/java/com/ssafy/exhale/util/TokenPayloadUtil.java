package com.ssafy.exhale.util;

import com.ssafy.exhale.dto.logicDto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class TokenPayloadUtil {
    private final JWTUtil jwtUtil;
    public CustomUserDetails getPayload() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }else{
            return null;
        }
    }
    // DB PK
    public long getMemberId(){
        CustomUserDetails customUserDetails = getPayload();
        if(customUserDetails==null)return -1;
        return customUserDetails.getMemberId();
    }

    // 로그인 아이디
    public String getLoginId(){
        CustomUserDetails customUserDetails = getPayload();
        if(customUserDetails==null) return null;
        return customUserDetails.getUsername();
    }
    // 닉네임
    public String getNickname(){
        CustomUserDetails customUserDetails = getPayload();
        if(customUserDetails==null) return null;
        return customUserDetails.getNickname();
    }

    // role
    public String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        return auth.getAuthority();
    }

    public String createJWT(){
        return jwtUtil.createJwt(this.getLoginId(), this.getMemberId(), this.getRole());
    }
    public String createRefreshToken(){
        return jwtUtil.createRefreshToken(this.getMemberId(),this.getRole());
    }
}

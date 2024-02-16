package com.ssafy.exhale.security;

import com.ssafy.exhale.domain.Member;
import com.ssafy.exhale.dto.logicDto.CustomUserDetails;
import com.ssafy.exhale.dto.logicDto.MemberDto;
import com.ssafy.exhale.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter  extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        if(jwtUtil.isExpired(token)){

            filterChain.doFilter(request,response);
            return;
        }

        String loginId  = jwtUtil.getLoginId(token);
        long userId  = jwtUtil.getmemberId(token);
        String role = jwtUtil.getRole(token);

        MemberDto memberDto = MemberDto.of();
        memberDto.setLoginId(loginId);
        memberDto.setId(userId);
        memberDto.setRole(role);
        Member member = memberDto.toEntity();

        CustomUserDetails customUserDetails = new CustomUserDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}

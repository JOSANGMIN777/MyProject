package com.ssafy.exhale.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            //토큰의 유효기간 만료
            tokenExpireException(response, e);
        } catch (JwtException | IllegalArgumentException e){
            //유효하지 않은 토큰
            tokenInvalidException(response, e);
        }
    }

    private void tokenExpireException(HttpServletResponse response, Exception exception) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(CommonResponse.connectionError(HttpStatus.valueOf(401),"Token Expiration").getBody()));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void tokenInvalidException(HttpServletResponse response, Exception exception) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(CommonResponse.connectionError(HttpStatus.valueOf(401),"Invalid token").getBody()));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

package com.ssafy.exhale.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private SecretKey secretKey;

    @Value("${spring.jwt.expiration}")
    private long jwtExpiration;

    @Value("${spring.jwt.refresh-token}")
    private long refreshTokenExpiration;
    public JWTUtil( @Value("${spring.jwt.secret}") String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

    }

    // JWT 검증
    public String getLoginId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("login_id",String.class);
    }

    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public long getmemberId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("member_id",Integer.class);
    }

    public Boolean isExpired(String token) throws ExpiredJwtException{

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());

    }


    // JWT 생성
    public String createJwt(String loginId, long id, String role){
        return Jwts.builder()
                .claim("login_id",loginId)
                .claim("member_id",id)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))   // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // 토큰 소멸시간
                .signWith(secretKey)
                .compact();
    }

    // refresh token 생성
    public String createRefreshToken(long id, String role){
        return Jwts.builder()
                .claim("member_id",id)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))   // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) // 토큰 소멸시간
                .signWith(secretKey)
                .compact();
    }
}

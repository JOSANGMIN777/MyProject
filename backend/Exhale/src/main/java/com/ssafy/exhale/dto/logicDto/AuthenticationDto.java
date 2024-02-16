package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.Authentication;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {
    private String id;
    private Long memberId;
    private String refreshValue;
    private LocalDateTime createdTime;
    private Long expireAt;
    private String key;

    public static AuthenticationDto of(){
        return new AuthenticationDto();
    }
    public static AuthenticationDto of(String id, Long memberId, String refreshValue, LocalDateTime createTime, Long expireAt, String key){
        return new AuthenticationDto(
                id,
                memberId,
                refreshValue,
                createTime,
                expireAt,
                key
        );
    }
    public Authentication toEntity(){
        return Authentication.of(
                id,
                memberId,
                refreshValue,
                createdTime,
                expireAt,
                key
        );
    }
}

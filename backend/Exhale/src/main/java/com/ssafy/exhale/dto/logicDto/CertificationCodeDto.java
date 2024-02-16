package com.ssafy.exhale.dto.logicDto;

import com.ssafy.exhale.domain.CertificationCode;
import com.ssafy.exhale.repository.CertificationCodeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationCodeDto {
    private String id;
    private String email;
    private String code;
    private LocalDateTime createAt;

    public static CertificationCodeDto of(){
        return new CertificationCodeDto();
    }

    public static CertificationCodeDto of(String id, String email, String code, LocalDateTime createAt){
        return new CertificationCodeDto(id, email, code, createAt);
    }

    public CertificationCode toEntity(){
        return CertificationCode.of(
                id,
                email,
                code,
                createAt
        );
    }

    public CertificationCodeDto fromDto(CertificationCode certificationCode){
        return CertificationCodeDto.of(
                certificationCode.getId(),
                certificationCode.getEmail(),
                certificationCode.getCode(),
                certificationCode.getCreateAt()
        );
    }
}

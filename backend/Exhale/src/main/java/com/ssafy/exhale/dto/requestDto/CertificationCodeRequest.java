package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.exhale.dto.logicDto.CertificationCodeDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CertificationCodeRequest {
    @NotBlank
    private String code;

    @NotBlank
    @JsonProperty("email_id")
    private String emailId;

    @NotBlank
    @JsonProperty("email_domain")
    private String emailDomain;
}



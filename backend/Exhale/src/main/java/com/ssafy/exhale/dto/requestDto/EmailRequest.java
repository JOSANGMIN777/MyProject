package com.ssafy.exhale.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailRequest {
    @NotBlank
    @JsonProperty("email_id")
    String emailId;

    @NotBlank
    @JsonProperty("email_domain")
    String emailDomain;
}

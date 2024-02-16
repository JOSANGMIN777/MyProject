package com.ssafy.exhale.dto.responseDto.rehabilitationDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FluencyCheckResponse {
    private boolean result;
    private String explain;

    public static FluencyCheckResponse of(boolean result, String explain) {
        return new FluencyCheckResponse(result, explain);
    }
}

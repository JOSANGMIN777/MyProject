package com.ssafy.exhale.controller;

import com.ssafy.exhale.dto.responseDto.commonDto.CommonResponse;
import com.ssafy.exhale.service.StatisticsService;
import com.ssafy.exhale.util.TokenPayloadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final TokenPayloadUtil tokenPayloadUtil;

    @GetMapping("/letter")
    public ResponseEntity<CommonResponse> getLetterData() {
        return CommonResponse.ok(statisticsService.getLetterData(tokenPayloadUtil.getMemberId()));
    }

    @GetMapping("/solved-data")
    public ResponseEntity<CommonResponse> getSolvedData() {
        return CommonResponse.ok(statisticsService.getSolvedData(tokenPayloadUtil.getMemberId()));
    }
}

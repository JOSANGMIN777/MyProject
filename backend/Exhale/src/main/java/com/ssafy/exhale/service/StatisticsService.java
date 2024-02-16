package com.ssafy.exhale.service;

import com.ssafy.exhale.dto.responseDto.statisticsDto.LetterDataResponse;
import com.ssafy.exhale.dto.responseDto.statisticsDto.SolvedDataResponse;
import com.ssafy.exhale.repository.rehabilitationRepository.LetterRecodeRepository;
import com.ssafy.exhale.repository.rehabilitationRepository.SolvedProblemRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final LetterRecodeRepository letterRecodeRepository;
    private final SolvedProblemRepositoryImpl solvedProblemRepository;

    public List<LetterDataResponse> getLetterData(Long memberId) {
        return letterRecodeRepository.getLetterRecode(memberId, PageRequest.of(0, 5));
    }

    public List<SolvedDataResponse> getSolvedData(Long memberId) {
        return solvedProblemRepository.getSolvedData(memberId);
    }
}

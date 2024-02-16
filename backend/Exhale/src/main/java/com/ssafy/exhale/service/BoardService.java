package com.ssafy.exhale.service;

import com.ssafy.exhale.domain.Board;
import com.ssafy.exhale.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> getBoardList(){
        return boardRepository.findAll();
    }
}

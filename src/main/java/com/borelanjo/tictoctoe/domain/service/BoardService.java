package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Board;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    Board init();

    Board play(Long boardId, Long columnId);

    Board find(Long boardId);
}

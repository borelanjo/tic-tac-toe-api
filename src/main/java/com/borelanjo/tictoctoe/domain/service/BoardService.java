package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Board;

public interface BoardService {

    Board init();

    Board play(Long boardId, Long columnId);

    Board find(Long boardId);

    void checkWinner(Board board);
}

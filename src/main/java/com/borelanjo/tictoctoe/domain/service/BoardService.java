package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;

import java.util.UUID;

public interface BoardService {

    Board init();

    Board play(UUID boardCode, RowPosition rowPosition, ColumnPosition columnPosition);

    Board find(UUID boardCode);

    void checkWinner(Board board);
}

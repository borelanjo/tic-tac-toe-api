package com.borelanjo.tictoctoe.repository;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;

import java.util.List;

public interface BoardRepository {

    Board init(List<Row> rows);

    Board find(final Long boardId);
}

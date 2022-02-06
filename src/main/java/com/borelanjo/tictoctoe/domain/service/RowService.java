package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;

import java.util.UUID;

public interface RowService {

    Row init(Board board, RowPosition position);

    Row find(final Long rowId);

    Row find(final UUID boardCode, RowPosition position);
}

package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;

import java.util.UUID;

public interface ColumnService {

    Column init(Row row, ColumnPosition position);

    Column findBy(RowPosition rowPosition, ColumnPosition columnPosition, UUID boardCode);

    Column play(Long columnId, Character square);
}

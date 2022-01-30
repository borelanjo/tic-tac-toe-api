package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;

public interface ColumnService {

    Column init(Row row);

    Column play(Long columnId, Character square);
}

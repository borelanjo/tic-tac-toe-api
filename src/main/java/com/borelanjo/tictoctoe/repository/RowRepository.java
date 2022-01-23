package com.borelanjo.tictoctoe.repository;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;

import java.util.List;

public interface RowRepository {

    Row init(final List<Column> columns);

    Row find(final Long rowId);
}

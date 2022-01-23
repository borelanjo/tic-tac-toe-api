package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.repository.ColumnRepository;
import com.borelanjo.tictoctoe.repository.RowRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class RowServiceImpl implements RowService {

    private final RowRepository rowRepository;

    @Override
    public Row init(final List<Column> columns) {
        return rowRepository.init(columns);
    }

    @Override
    public Row find(Long rowId) {
        return rowRepository.find(rowId);
    }
}

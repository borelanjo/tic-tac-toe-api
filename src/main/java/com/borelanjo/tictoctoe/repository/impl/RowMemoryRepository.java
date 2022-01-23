package com.borelanjo.tictoctoe.repository.impl;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.repository.RowRepository;

import java.util.ArrayList;
import java.util.List;

public class RowMemoryRepository implements RowRepository {

    private Long currentId = 1L;

    private final List<Row> rows = new ArrayList<>();

    @Override
    public Row init(final List<Column> columns) {
        final var row = Row.builder()
                .id(currentId++)
                .columns(columns)
                .build();
        rows.add(row);
        return row;
    }

    @Override
    public Row find(Long id) {
        return rows
                .stream()
                .filter(c -> c.getId().equals(id))
                .findAny()
                .orElseThrow();
    }
}
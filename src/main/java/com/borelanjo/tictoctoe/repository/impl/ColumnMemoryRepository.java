package com.borelanjo.tictoctoe.repository.impl;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.repository.ColumnRepository;

import java.util.ArrayList;
import java.util.List;

public class ColumnMemoryRepository implements ColumnRepository {

    private Long currentId = 1L;

    private final List<Column> columns = new ArrayList<>();

    @Override
    public Column init() {
        final var column = Column.builder()
                .id(currentId++)
                .build();
        columns.add(column);
        return column;
    }

    @Override
    public Column play(final Column column, final Character square) {
        column.setSquare(square);

        return column;
    }

    @Override
    public Column find(Long id) {
        return columns
                .stream()
                .filter(c -> c.getId().equals(id))
                .findAny()
                .orElseThrow();
    }
}

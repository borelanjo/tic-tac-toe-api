package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyPlayedThisColumnException;
import com.borelanjo.tictoctoe.application.service.exception.InvalidInputSquareException;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.ColumnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;

    @Override
    public Column init(final Row row) {
        final var column = Column.builder()
                .code(UUID.randomUUID())
                .row(row)
                .createdAt(LocalDateTime.now())
                .build();
        return columnRepository.save(column);
    }

    @Override
    public Column play(Long columnId, Character square) {
        validateInput(square);

        Column column = columnRepository.findById(columnId).orElseThrow();

        validatePlayable(column);

        column.setSquare(square);
        column.setUpdatedAt(LocalDateTime.now());

        return columnRepository.save(column);
    }

    private void validatePlayable(Column column) {
        if (Objects.nonNull(column.getSquare())){
            throw new AlreadyPlayedThisColumnException();
        }
    }

    private void validateInput(Character square) {
        if ('X' != square && 'O' != square) {
            throw new InvalidInputSquareException(square);
        }
    }
}

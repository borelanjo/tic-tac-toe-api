package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyPlayedThisColumnException;
import com.borelanjo.tictoctoe.application.service.exception.InvalidInputSquareException;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
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
    public Column init(final Row row, final ColumnPosition position) {
        final var column = Column.builder()
                .code(UUID.randomUUID())
                .row(row)
                .position(position)
                .createdAt(LocalDateTime.now())
                .build();
        return columnRepository.save(column);
    }

    @Override
    public Column play(final Long columnId, final Character square) {
        validateInput(square);

        final Column column = columnRepository.findById(columnId).orElseThrow();

        validatePlayable(column);

        column.setSquare(square);
        column.setUpdatedAt(LocalDateTime.now());

        return columnRepository.save(column);
    }

    @Override
    public Column findBy(final RowPosition rowPosition, final ColumnPosition columnPosition, final UUID boardCode) {
        return columnRepository
                .findByPositionAndRowPositionAndRowBoardCode(columnPosition, rowPosition, boardCode)
                .orElseThrow();
    }

    private void validatePlayable(final Column column) {
        if (Objects.nonNull(column.getSquare())){
            throw new AlreadyPlayedThisColumnException();
        }
    }

    private void validateInput(final Character square) {
        if ('X' != square && 'O' != square) {
            throw new InvalidInputSquareException(square);
        }
    }
}

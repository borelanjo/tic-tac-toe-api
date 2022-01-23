package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyPlayedThisColumnException;
import com.borelanjo.tictoctoe.application.service.exception.InvalidInputSquareException;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.repository.ColumnRepository;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;

    @Override
    public Column init() {
        return columnRepository.init();
    }

    @Override
    public Column play(Long columnId, Character square) {
        validateInput(square);

        Column column = columnRepository.find(columnId);

        validatePlayable(column);

        return columnRepository.play(column, square);
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

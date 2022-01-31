package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.RowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RowServiceImpl implements RowService {

    private final RowRepository rowRepository;

    @Override
    public Row init(final Board board, final RowPosition position) {
        final var row = Row.builder()
                .board(board)
                .code(UUID.randomUUID())
                .position(position)
                .createdAt(LocalDateTime.now())
                .build();
        return rowRepository.save(row);
    }

    @Override
    public Row find(Long rowId) {
        return rowRepository.findById(rowId).orElseThrow();
    }
}

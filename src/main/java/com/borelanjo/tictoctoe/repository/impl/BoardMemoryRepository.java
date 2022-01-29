package com.borelanjo.tictoctoe.repository.impl;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.repository.BoardRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardMemoryRepository implements BoardRepository {

    private Long currentId = 1L;

    private final List<Board> boards = new ArrayList<>();

    @Override
    public Board init(final List<Row> rows) {
        Board board = Board.builder()
                .id(currentId++)
                .input('X')
                .rows(rows)
                .build();
        boards.add(board);
        return board;
    }

    @Override
    public Board find(Long id) {
        return boards
                .stream()
                .filter(c -> c.getId().equals(id))
                .findAny()
                .orElseThrow();
    }
}

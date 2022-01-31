package com.borelanjo.tictoctoe.presentation.mapper;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.presentation.dto.board.BoardResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.column.ColumnResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.row.RowResponseTo;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class BoardMapper {

    public static BoardResponseTo from(Board board) {
        return BoardResponseTo
                .builder()
                .code(board.getCode())
                .input(board.getInput())
                .winner(board.getWinner())
                .rows(board
                        .getRows().stream()
                        .map(r -> RowResponseTo.builder()
                                .position(r.getPosition())
                                .columns(r.getColumns().stream()
                                        .map(c -> ColumnResponseTo
                                        .builder()
                                        .position(c.getPosition())
                                        .square(c.getSquare())
                                                .build()
                                        ).collect(Collectors.toList())
                                )
                                .build()).collect(Collectors.toList())
                )
                .build();
    }
}

package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@AllArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final RowService rowService;

    private final ColumnService columnService;

    @Override
    public Board init() {
        return boardRepository.init(Arrays.asList(
                initRow(), initRow(), initRow()
        ));
    }

    @Override
    public Board play(Long boardId, Long columnId) {
        Board board = boardRepository.find(boardId);
        columnService.play(columnId, board.getInput());
        switchInput(board);
        log.info("play={}",board);
        return board;
    }

    @Override
    public Board find(Long boardId) {
        return boardRepository.find(boardId);
    }

    private void switchInput(final Board board) {
        board.setInput(board.getInput() == 'X' ? 'O' : 'X');
    }

    private Row initRow() {
        return rowService.init(Arrays.asList(
                columnService.init(),
                columnService.init(),
                columnService.init())
        );
    }
}

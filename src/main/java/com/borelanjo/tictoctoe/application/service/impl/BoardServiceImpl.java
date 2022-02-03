package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyWinnerException;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final RowService rowService;

    private final ColumnService columnService;

    @Override
    public Board init() {
        final Board board = boardRepository.save(Board.builder()
                .input('X')
                .code(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build());

        initRow(board, RowPosition.TOP);
        initRow(board, RowPosition.MIDDLE);
        initRow(board, RowPosition.BOTTOM);

        return find(board.getCode());
    }

    @Override
    public Board play(final UUID boardCode, final RowPosition rowPosition, final ColumnPosition columnPosition) {
        Board board = find(boardCode);

        validateWinner(board);

        final Column column = columnService.findBy(rowPosition, columnPosition, board.getCode());

        columnService.play(column.getId(), board.getInput());

        switchInput(board);
        checkWinner(board);
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        log.info("play={}", board);
        return board;
    }

    @Override
    public Board find(final UUID boardCode) {
        return boardRepository
                .findByCode(boardCode)
                .orElseThrow();
    }

    @Override
    public void checkWinner(final Board board) {
        final Optional<Character> winnerX = findWinner(board, 'X');
        if (winnerX.isPresent()) {
            board.setWinner(winnerX.get());
            return;
        }

        findWinner(board, 'O').ifPresent(board::setWinner);

    }

    private void validateWinner(Board board) {
        if (board.getWinner() != null) {
            throw new AlreadyWinnerException(board.getWinner());
        }
    }

    private Optional<Character> findWinner(final Board board, final Character character) {
        return isSameLineWinner(board, character)
                || isSameColumnWinner(board, character)
                || isDiagonalWinner(board, character)
                ? Optional.of(character) : Optional.empty();
    }

    private boolean isDiagonalWinner(final Board board, final Character character) {
        final int winnerResult = character*3;
        return sumDiagonalLeft(board) == winnerResult || sumDiagonalRight(board) == winnerResult ;
    }

    private int sumDiagonalLeft(final Board board) {
        final var squareLeft = columnService.findBy(RowPosition.TOP, ColumnPosition.LEFT, board.getCode()).getSquare();
        final var squareMiddle = columnService.findBy(RowPosition.MIDDLE, ColumnPosition.MIDDLE, board.getCode()).getSquare();
        final var squareRight = columnService.findBy(RowPosition.BOTTOM, ColumnPosition.RIGHT, board.getCode()).getSquare();
        return squareLeft != null && squareMiddle != null && squareRight != null
                ? squareLeft+squareMiddle+squareRight
                : 0;
    }

    private int sumDiagonalRight(final Board board) {
        final var squareRight = columnService.findBy(RowPosition.TOP, ColumnPosition.RIGHT, board.getCode()).getSquare();
        final var squareMiddle = columnService.findBy(RowPosition.MIDDLE, ColumnPosition.MIDDLE, board.getCode()).getSquare();
        final var squareLeft = columnService.findBy(RowPosition.BOTTOM, ColumnPosition.LEFT, board.getCode()).getSquare();
        return squareRight != null && squareMiddle != null && squareLeft != null
                ? squareRight+squareMiddle+squareLeft
                : 0;
    }

    private boolean isSameLineWinner(final Board board, final Character character) {
        final int winnerResult = character*3;

        return sumColumnsOfRow(board, RowPosition.TOP) == winnerResult
                || sumColumnsOfRow(board, RowPosition.MIDDLE) == winnerResult
                || sumColumnsOfRow(board, RowPosition.BOTTOM) == winnerResult;
    }

    private int sumColumnsOfRow(final Board board, final RowPosition rowPosition) {
        final var squareLeft = columnService.findBy(rowPosition, ColumnPosition.LEFT, board.getCode()).getSquare();
        final var squareMiddle = columnService.findBy(rowPosition, ColumnPosition.MIDDLE, board.getCode()).getSquare();
        final var squareRight = columnService.findBy(rowPosition, ColumnPosition.RIGHT, board.getCode()).getSquare();
        return squareLeft != null && squareMiddle != null && squareRight != null
                ? squareLeft+squareMiddle+squareRight
                : 0;
    }

    private boolean isSameColumnWinner(final Board board, final Character character) {
        final int winnerResult = character * 3;

        return sumSameLineColumn(board, ColumnPosition.LEFT) == winnerResult
                || sumSameLineColumn(board, ColumnPosition.MIDDLE) == winnerResult
                || sumSameLineColumn(board, ColumnPosition.RIGHT) == winnerResult;
    }

    private int sumSameLineColumn(final Board board, final ColumnPosition columnPosition) {
        final var squareTop = columnService.findBy(RowPosition.TOP, columnPosition, board.getCode()).getSquare();
        final var squareMiddle = columnService.findBy(RowPosition.MIDDLE, columnPosition, board.getCode()).getSquare();
        final var squareBottom = columnService.findBy(RowPosition.BOTTOM, columnPosition, board.getCode()).getSquare();
        return squareTop != null && squareMiddle != null && squareBottom != null
                ? squareTop+squareMiddle+squareBottom
                : 0;
    }

    private void switchInput(final Board board) {
        board.setInput(board.getInput() == 'X' ? 'O' : 'X');
    }

    private void initRow(final Board board, final RowPosition position) {
        final Row row = rowService.init(board, position);

        columnService.init(row, ColumnPosition.LEFT);
        columnService.init(row, ColumnPosition.MIDDLE);
        columnService.init(row, ColumnPosition.RIGHT);
    }
}

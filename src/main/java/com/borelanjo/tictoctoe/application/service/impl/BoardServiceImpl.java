package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyWinnerException;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@AllArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final RowService rowService;

    private final ColumnService columnService;

    @Override
    public Board init() {
        Board board = boardRepository.save(Board.builder()
                .input('X')
                .code(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build());

        initRow(board);
        initRow(board);
        initRow(board);

        return find(board.getId());
    }

    @Override
    public Board play(Long boardId, Long columnId) {
        Board board = find(boardId);

        validateWinner(board);

        columnService.play(columnId, board.getInput());

        switchInput(board);
        checkWinner(board);
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        log.info("play={}", board);
        return board;
    }

    @Override
    public Board find(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }

    @Override
    public void checkWinner(Board board) {
        Optional<Character> winnerX = findWinner(board, 'X');
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

    private boolean isDiagonalWinner(Board board, Character character) {
        ArrayList<Row> rows = new ArrayList<>(board.getRows());
        return IntStream.range(0, board.getRows().size())
                .allMatch(i -> {
                    ArrayList<Column> columns = new ArrayList<>(rows.get(i).getColumns());
                    return columns.get(i).getSquare() == character;
                })
                || IntStream.range(0, board.getRows().size())
                .allMatch(i -> {
                    ArrayList<Column> columns = new ArrayList<>(rows.get(i).getColumns());
                    return columns.get(2 - i).getSquare() == character;
                })
                ;
    }

    private boolean isSameLineWinner(Board board, Character character) {
        return board.getRows()
                .stream()
                .anyMatch(r ->
                        r.getColumns().stream()
                                .allMatch(c -> c.getSquare() != null
                                        && c.getSquare().equals(character)
                                )
                );
    }

    private boolean isSameColumnWinner(Board board, Character character) {
        return isWinnerSameLineColumn(board, character, 0)
                || isWinnerSameLineColumn(board, character, 1)
                || isWinnerSameLineColumn(board, character, 2);
    }

    private boolean isWinnerSameLineColumn(final Board board, final Character character, final int columnIndex) {
        ArrayList<Row> rows = new ArrayList<>(board.getRows());
        ArrayList<Column> columnsRow1 = new ArrayList<>(rows.get(0).getColumns());
        ArrayList<Column> columnsRow2 = new ArrayList<>(rows.get(1).getColumns());
        ArrayList<Column> columnsRow3 = new ArrayList<>(rows.get(2).getColumns());
        return columnsRow1.get(columnIndex).getSquare() == character
                && columnsRow2.get(columnIndex).getSquare() == character
                && columnsRow3.get(columnIndex).getSquare() == character;
    }

    private void switchInput(final Board board) {
        board.setInput(board.getInput() == 'X' ? 'O' : 'X');
    }

    private void initRow(final Board board) {
        Row row = rowService.init(board);

        columnService.init(row);
        columnService.init(row);
        columnService.init(row);
    }
}

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

import java.util.Arrays;
import java.util.Optional;
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
        return boardRepository.init(Arrays.asList(
                initRow(), initRow(), initRow()
        ));
    }

    @Override
    public Board play(Long boardId, Long columnId) {
        Board board = boardRepository.find(boardId);

        validateWinner(board);

        Column column = columnService.play(columnId, board.getInput());

        board.getRows().forEach(r-> {
            for (int i = 0; i < r.getColumns().size(); i++){
                if(r.getColumns().get(i).equals(column)){
                    r.getColumns().set(i, column);
                }
            }
        });

        switchInput(board);
        checkWinner(board);

        log.info("play={}", board);
        return board;
    }

    @Override
    public Board find(Long boardId) {
        return boardRepository.find(boardId);
    }

    @Override
    public void checkWinner(Board board) {
        Optional<Character> winnerX = findWinner(board, 'X');
        if (winnerX.isPresent()) {
            board.setWinner(winnerX.get());
            return;
        }

        findWinner(board,  'O').ifPresent(board::setWinner);

    }

    private void validateWinner(Board board) {
        if (board.getWinner() != null) {
            throw new AlreadyWinnerException(board.getWinner());
        }
    }

    private Optional<Character> findWinner(final Board board, final Character character) {
        return  isSameLineWinner(board, character)
                || isSameColumnWinner(board, character)
                ||  isDiagonalWinner(board, character)
                ? Optional.of(character) : Optional.empty();
    }

    private boolean isDiagonalWinner(Board board, Character character) {

        return IntStream.range(0, board.getRows().size())
                .allMatch(i -> board.getRows().get(i).getColumns().get(i).getSquare() == character)
                || IntStream.range(0, board.getRows().size())
                .allMatch(i -> board.getRows().get(i).getColumns().get(2 - i).getSquare() == character)
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
        return board.getRows().get(0).getColumns().get(columnIndex).getSquare() == character
                && board.getRows().get(1).getColumns().get(columnIndex).getSquare() == character
                && board.getRows().get(2).getColumns().get(columnIndex).getSquare() == character;
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

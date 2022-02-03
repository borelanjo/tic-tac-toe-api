package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyWinnerException;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class BoardServiceIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void shouldInitANewGameWhenRequest() {
        final Long expectedBoardId = getNextBoardId();
        final var board = boardService.init();

        Assertions.assertNotNull(board);
        Assertions.assertEquals(expectedBoardId, board.getId());

        final var rowTop = getRow(board, RowPosition.TOP);
        final var rowTopColumnLeft = getColumn(rowTop, ColumnPosition.LEFT);
        final var rowTopColumnMiddle = getColumn(rowTop, ColumnPosition.MIDDLE);
        final var rowTopColumnRight = getColumn(rowTop, ColumnPosition.RIGHT);

        final var rowMiddle = getRow(board, RowPosition.MIDDLE);
        final var rowMiddleColumnLeft = getColumn(rowMiddle, ColumnPosition.LEFT);
        final var rowMiddleColumnMiddle = getColumn(rowMiddle, ColumnPosition.MIDDLE);
        final var rowMiddleColumnRight = getColumn(rowMiddle, ColumnPosition.RIGHT);

        final var rowBottom = getRow(board, RowPosition.BOTTOM);
        final var rowBottomColumnLeft = getColumn(rowBottom, ColumnPosition.LEFT);
        final var rowBottomColumnMiddle = getColumn(rowBottom, ColumnPosition.MIDDLE);
        final var rowBottomColumnRight = getColumn(rowBottom, ColumnPosition.RIGHT);

        Assertions.assertNotNull(rowTop);
        Assertions.assertEquals(RowPosition.TOP, rowTop.getPosition());
        Assertions.assertNotNull(rowTopColumnLeft);
        Assertions.assertNotNull(rowTopColumnMiddle);
        Assertions.assertNotNull(rowTopColumnRight);
        Assertions.assertNull(rowTopColumnLeft.getSquare());
        Assertions.assertNull(rowTopColumnMiddle.getSquare());
        Assertions.assertNull(rowTopColumnRight.getSquare());

        Assertions.assertNotNull(rowMiddle);
        Assertions.assertEquals(RowPosition.MIDDLE, rowMiddle.getPosition());
        Assertions.assertNotNull(rowMiddleColumnLeft);
        Assertions.assertNotNull(rowMiddleColumnMiddle);
        Assertions.assertNotNull(rowMiddleColumnRight);
        Assertions.assertNull(rowMiddleColumnLeft.getSquare());
        Assertions.assertNull(rowMiddleColumnMiddle.getSquare());
        Assertions.assertNull(rowMiddleColumnRight.getSquare());

        Assertions.assertNotNull(rowBottom);
        Assertions.assertEquals(RowPosition.BOTTOM, rowBottom.getPosition());
        Assertions.assertNotNull(rowBottomColumnLeft);
        Assertions.assertNotNull(rowBottomColumnMiddle);
        Assertions.assertNotNull(rowBottomColumnRight);
        Assertions.assertNull(rowBottomColumnLeft.getSquare());
        Assertions.assertNull(rowBottomColumnMiddle.getSquare());
        Assertions.assertNull(rowBottomColumnRight.getSquare());

    }

    @Test
    public void shouldPlay() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);

        final var board = boardService.find(boardCode);
        final var rowTop = getRow(board, RowPosition.TOP);
        final var rowTopColumnLeft = getColumn(rowTop, ColumnPosition.LEFT);
        final var rowTopColumnMiddle = getColumn(rowTop, ColumnPosition.MIDDLE);
        final var rowTopColumnRight = getColumn(rowTop, ColumnPosition.RIGHT);


        Assertions.assertEquals('X', rowTopColumnLeft.getSquare());
        Assertions.assertEquals('O', rowTopColumnMiddle.getSquare());
        Assertions.assertEquals('X', rowTopColumnRight.getSquare());

    }

    @Test
    public void shouldXWinWhenSameRowHasXAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldXWinWhenSameColumnHasXAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.LEFT);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameRowHasOAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.RIGHT);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldXWinWhenSameDiagonalHasXAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.RIGHT);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameDiagonalHasXAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.LEFT);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameColumnHasXAtAllSquare() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.BOTTOM, ColumnPosition.MIDDLE);

        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.MIDDLE);

        Board result = boardService.find(boardCode);
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldNotPlayWhenAlreadyExistsAWinner() {
        var boardCode = boardService.init().getCode();

        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.MIDDLE);
        boardService.play(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);

        final var exception = Assertions.assertThrows(
                AlreadyWinnerException.class, () -> boardService
                        .play(boardCode, RowPosition.TOP, ColumnPosition.RIGHT));

        Assertions.assertEquals(
                String.format(AlreadyWinnerException.MESSAGE, 'X'), exception.getMessage());

    }

    private long getNextBoardId() {
        List<Board> boards = boardRepository.findAll();
        return boards.isEmpty() ? 1L : boards.stream().max(Comparator.comparing(Board::getId)).get().getId() + 1;
    }

    private Row getRow(Board board, RowPosition top) {
        return board.getRows().stream()
                .filter(r -> r.getPosition().equals(top)).findFirst().orElseThrow();
    }


    private Column getColumn(Row rowTop, ColumnPosition left) {
        return rowTop.getColumns().stream()
                .filter(c -> c.getPosition().equals(left)).findFirst().orElseThrow();
    }

}
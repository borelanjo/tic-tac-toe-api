package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.consumer.BoardConsumer;
import com.borelanjo.tictoctoe.application.consumer.ColumnConsumer;
import com.borelanjo.tictoctoe.application.consumer.RowConsumer;
import com.borelanjo.tictoctoe.application.service.exception.AlreadyWinnerException;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import com.borelanjo.tictoctoe.presentation.dto.column.ColumnRequestTo;
import com.borelanjo.tictoctoe.presentation.dto.row.RowRequestTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

@SpringBootTest
@DirtiesContext
class BoardServiceIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @SpyBean
    private BoardConsumer boardConsumer;

    @SpyBean
    private RowConsumer rowConsumer;

    @SpyBean
    private ColumnConsumer columnConsumer;

    @Test
    public void shouldInitANewGameWhenRequest() {
        final var board = boardService.init(UUID.randomUUID());

        Assertions.assertNotNull(board);
        Assertions.assertEquals('X', board.getInput());
        Assertions.assertNull(board.getWinner());
        Assertions.assertNull(board.getUpdatedAt());

    }

    @Test
    public void shouldPlay() {
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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
        final UUID boardCode = initGame();

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

    private Row getRow(final Board board, final RowPosition position) {
        return board.getRows().stream()
                .filter(r -> r.getPosition().equals(position)).findFirst().orElseThrow();
    }

    private Column getColumn(final Row row, final ColumnPosition position) {
        return row.getColumns().stream()
                .filter(c -> c.getPosition().equals(position)).findFirst().orElseThrow();
    }

    private UUID initGame() {
        final var boardCode = UUID.randomUUID();

        boardConsumer.receiveInit(boardCode);

        rowReceiveInit(boardCode, RowPosition.TOP);

        columnReceiveInit(boardCode, RowPosition.TOP, ColumnPosition.LEFT);

        columnReceiveInit(boardCode, RowPosition.TOP, ColumnPosition.MIDDLE);

        columnReceiveInit(boardCode, RowPosition.TOP, ColumnPosition.RIGHT);

        rowReceiveInit(boardCode, RowPosition.MIDDLE);

        columnReceiveInit(boardCode, RowPosition.MIDDLE, ColumnPosition.LEFT);

        columnReceiveInit(boardCode, RowPosition.MIDDLE, ColumnPosition.MIDDLE);

        columnReceiveInit(boardCode, RowPosition.MIDDLE, ColumnPosition.RIGHT);

        rowReceiveInit(boardCode, RowPosition.BOTTOM);

        columnReceiveInit(boardCode, RowPosition.BOTTOM, ColumnPosition.LEFT);

        columnReceiveInit(boardCode, RowPosition.BOTTOM, ColumnPosition.MIDDLE);

        columnReceiveInit(boardCode, RowPosition.BOTTOM, ColumnPosition.RIGHT);
        return boardCode;
    }

    private void columnReceiveInit(UUID boardCode, RowPosition middle, ColumnPosition left) {
        columnConsumer.receiveInit(ColumnRequestTo
                .builder()
                .boardCode(boardCode)
                .rowPosition(middle)
                .position(left)
                .build());
    }

    private void rowReceiveInit(UUID boardCode, RowPosition top) {
        rowConsumer.receiveInit(RowRequestTo
                .builder()
                .boardCode(boardCode)
                .position(top)
                .build());
    }

}
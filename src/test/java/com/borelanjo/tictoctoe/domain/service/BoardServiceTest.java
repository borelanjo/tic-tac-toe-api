package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.impl.BoardServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.when;

class BoardServiceTest {

    private BoardService boardService;
    private BoardRepository boardRepository;
    private AtomicLong columnId = new AtomicLong(1);
    private RowService rowService;
    private ColumnService columnService;

    @BeforeEach
    private void setup() {
        boardRepository = Mockito.mock(BoardRepository.class);
        rowService = Mockito.mock(RowService.class);
        columnService = Mockito.mock(ColumnService.class);
        boardService = new BoardServiceImpl(
                boardRepository,
                rowService,
                columnService
                );

    }

    @Test
    public void shouldInitANewGameWhenRequest() {
        Board expectedBoard = Board.builder()
                .id(1L)
                .code(UUID.randomUUID())
                .input('X')
                .createdAt(LocalDateTime.now()).build();

        mockBoardRepository(expectedBoard);

        mockInitRows(expectedBoard);
        Board board = boardService.init();

        Assertions.assertNotNull(board);
        Assertions.assertEquals(expectedBoard.getId(), board.getId());
        Assertions.assertEquals(expectedBoard.getCode(), board.getCode());
        Assertions.assertNull(board.getWinner());
        Assertions.assertEquals(3, board.getRows().size());

    }

    @Test
    public void shouldPlay() {

        final Board expectedBoard = Board.builder()
                .id(1L)
                .code(UUID.randomUUID())
                .input('X')
                .createdAt(LocalDateTime.now()).build();



        mockInitRows(expectedBoard);

        when(boardRepository.findByCode(expectedBoard.getCode())).thenReturn(Optional.of(expectedBoard));

        mockColumnFind(RowPosition.TOP, ColumnPosition.LEFT, expectedBoard);
        mockColumnFind(RowPosition.TOP, ColumnPosition.MIDDLE, expectedBoard);
        mockColumnFind(RowPosition.TOP, ColumnPosition.RIGHT, expectedBoard);
        mockColumnFind(RowPosition.MIDDLE, ColumnPosition.LEFT, expectedBoard);
        mockColumnFind(RowPosition.MIDDLE, ColumnPosition.MIDDLE, expectedBoard);
        mockColumnFind(RowPosition.MIDDLE, ColumnPosition.RIGHT, expectedBoard);
        mockColumnFind(RowPosition.BOTTOM, ColumnPosition.LEFT, expectedBoard);
        mockColumnFind(RowPosition.BOTTOM, ColumnPosition.MIDDLE, expectedBoard);
        mockColumnFind(RowPosition.BOTTOM, ColumnPosition.RIGHT, expectedBoard);

        mockBoardRepository(expectedBoard);

        Board board = boardService.init();


        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.LEFT);
        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.MIDDLE);
        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.RIGHT);

        Board result = boardService.find(board.getCode());
        Assertions.assertEquals(3, result.getRows().size());
        Assertions.assertNotNull(result.getUpdatedAt());

    }

    private void mockColumnFind(RowPosition top, ColumnPosition left, Board expectedBoard) {
        when(columnService.findBy(top, left, expectedBoard.getCode()))
                .thenReturn(expectedBoard
                        .getRows().
                        stream()
                        .filter(r -> r.getPosition().equals(top))
                        .findFirst()
                        .get()
                        .getColumns()
                        .stream()
                        .filter(c -> c.getPosition().equals(left)).findFirst().get());
    }

    @Test
    public void shouldXWinWhenSameRowHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.RIGHT);
        boardService.play(board.getCode(), RowPosition.MIDDLE, ColumnPosition.RIGHT);
        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.MIDDLE);
        boardService.play(board.getCode(), RowPosition.MIDDLE, ColumnPosition.LEFT);
        boardService.play(board.getCode(), RowPosition.TOP, ColumnPosition.LEFT);

        Board result = boardService.find(board.getCode());
        Assertions.assertEquals('X', result.getWinner());

    }

//    @Test
//    public void shouldXWinWhenSameColumnHasXAtAllSquare() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
//
//        Board result = boardService.find(board.getId());
//        Assertions.assertEquals('X', result.getWinner());
//
//    }
//
//    @Test
//    public void shouldOWinWhenSameRowHasXAtAllSquare() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());
//
//        Board result = boardService.find(board.getId());
//        Assertions.assertEquals('O', result.getWinner());
//
//    }
//
//    @Test
//    public void shouldXWinWhenSameDiagonalHasXAtAllSquare() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());
//
//        Board result = boardService.find(board.getId());
//        Assertions.assertEquals('X', result.getWinner());
//
//    }
//
//    @Test
//    public void shouldOWinWhenSameDiagonalHasXAtAllSquare() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
//
//        Board result = boardService.find(board.getId());
//        Assertions.assertEquals('O', result.getWinner());
//
//    }
//
//    @Test
//    public void shouldOWinWhenSameColumnHasXAtAllSquare() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
//
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
//
//        Board result = boardService.find(board.getId());
//        Assertions.assertEquals('O', result.getWinner());
//
//    }
//
//    @Test
//    public void shouldNotPlayWhenAlreadyExistsAWinner() {
//        Board board = boardService.init();
//
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
//        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
//        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());
//
//        final var exception = Assertions.assertThrows(
//                AlreadyWinnerException.class, () -> boardService
//                        .play(board.getId(), board.getRows().get(0).getColumns().get(2).getId()));
//
//        Assertions.assertEquals(
//                String.format(AlreadyWinnerException.MESSAGE, 'X'), exception.getMessage());
//
//    }

    private void mockInitRows(final Board expectedBoard) {
        var rowTop = mockRow(expectedBoard, RowPosition.TOP, 1L);

        var rowMiddle = mockRow(expectedBoard, RowPosition.MIDDLE, 2L);

        var rowBottom = mockRow(expectedBoard, RowPosition.BOTTOM, 3L);

        mockInitColumns(rowTop, 1L);
        mockInitColumns(rowMiddle, 4L);
        mockInitColumns(rowBottom, 7L);
    }

    private void mockInitColumns(final Row row, final Long initialColumnId) {
        initColumn(row, ColumnPosition.RIGHT, initialColumnId);

        initColumn(row, ColumnPosition.MIDDLE, initialColumnId+1);

        initColumn(row, ColumnPosition.LEFT, initialColumnId+2);
    }

    private void initColumn(final Row row, final ColumnPosition columnPosition, final Long id) {
        var column = Column
                .builder()
                .createdAt(LocalDateTime.now())
                .row(row)
                .position(columnPosition)
                .id(id)
                .code(UUID.randomUUID())
                .build();

        if(row.getColumns() == null){
            row.setColumns(new HashSet<>());
        }

        row.getColumns().add(column);

        when(columnService.init(row, columnPosition)).then(r -> column);
    }

    private Row mockRow(final Board expectedBoard, final RowPosition rowPosition, final long id) {

        Row row = Row
                .builder()
                .createdAt(LocalDateTime.now())
                .board(expectedBoard)
                .position(rowPosition)
                .id(id)
                .code(UUID.randomUUID())
                .build();

        if (expectedBoard.getRows() == null){
            expectedBoard.setRows(new HashSet<>());
        }

        expectedBoard.getRows().add(row);

        when(rowService.init(expectedBoard, rowPosition)).then(r -> row);
        return row;
    }

    private void mockBoardRepository(Board expectedBoard) {
        when(boardRepository.save(Mockito.any(Board.class)))
                .thenReturn(expectedBoard);

        when(boardRepository.findByCode(expectedBoard.getCode()))
                .thenReturn(Optional.of(expectedBoard));
    }

}
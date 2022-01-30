package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyWinnerException;
import com.borelanjo.tictoctoe.application.service.impl.BoardServiceImpl;
import com.borelanjo.tictoctoe.application.service.impl.ColumnServiceImpl;
import com.borelanjo.tictoctoe.application.service.impl.RowServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.BoardRepository;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.ColumnRepository;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.RowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.when;

class BoardServiceTest {

    private BoardService boardService;
    private ColumnRepository columnRepository;
    private AtomicLong columnId = new AtomicLong(1);

    @BeforeEach
    private void setup() {
        columnRepository = Mockito.mock(ColumnRepository.class);
        boardService = new BoardServiceImpl(
                Mockito.mock(BoardRepository.class),
                new RowServiceImpl(Mockito.mock(RowRepository.class)),
                new ColumnServiceImpl(columnRepository)
                );

//        mockColumnRepositorySave(getBuildColumn(LocalDateTime.now()).build());
    }

    @Test
    public void shouldInitANewGameWhenRequest() {
        Board board = boardService.init();

        Assertions.assertNotNull(board);
        Assertions.assertEquals(1, board.getId());

        Assertions.assertNotNull(board.getRows().get(0));
        Assertions.assertEquals(1, board.getRows().get(0).getId());
        Assertions.assertNotNull(board.getRows().get(0).getColumns().get(0));
        Assertions.assertNotNull(board.getRows().get(0).getColumns().get(1));
        Assertions.assertNotNull(board.getRows().get(0).getColumns().get(2));
        Assertions.assertNull(board.getRows().get(0).getColumns().get(0).getSquare());
        Assertions.assertNull(board.getRows().get(0).getColumns().get(1).getSquare());
        Assertions.assertNull(board.getRows().get(0).getColumns().get(2).getSquare());

        Assertions.assertNotNull(board.getRows().get(1));
        Assertions.assertEquals(2, board.getRows().get(1).getId());
        Assertions.assertNotNull(board.getRows().get(1).getColumns().get(0));
        Assertions.assertNotNull(board.getRows().get(1).getColumns().get(1));
        Assertions.assertNotNull(board.getRows().get(1).getColumns().get(2));
        Assertions.assertNull(board.getRows().get(1).getColumns().get(0).getSquare());
        Assertions.assertNull(board.getRows().get(1).getColumns().get(1).getSquare());
        Assertions.assertNull(board.getRows().get(1).getColumns().get(2).getSquare());

        Assertions.assertNotNull(board.getRows().get(2));
        Assertions.assertEquals(3, board.getRows().get(2).getId());
        Assertions.assertNotNull(board.getRows().get(2).getColumns().get(0));
        Assertions.assertNotNull(board.getRows().get(2).getColumns().get(1));
        Assertions.assertNotNull(board.getRows().get(2).getColumns().get(2));
        Assertions.assertNull(board.getRows().get(2).getColumns().get(0).getSquare());
        Assertions.assertNull(board.getRows().get(2).getColumns().get(1).getSquare());
        Assertions.assertNull(board.getRows().get(2).getColumns().get(2).getSquare());

    }

    @Test
    public void shouldPlay() {
        Board board = boardService.init();

        board.getRows().get(0).getColumns().forEach(c -> mockRepositoryFindById(c));

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('X', result.getRows().get(0).getColumns().get(0).getSquare());
        Assertions.assertEquals('O', result.getRows().get(0).getColumns().get(1).getSquare());
        Assertions.assertEquals('X', result.getRows().get(0).getColumns().get(2).getSquare());

    }

    @Test
    public void shouldXWinWhenSameRowHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldXWinWhenSameColumnHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameRowHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldXWinWhenSameDiagonalHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('X', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameDiagonalHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldOWinWhenSameColumnHasXAtAllSquare() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(1).getId());

        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('O', result.getWinner());

    }

    @Test
    public void shouldNotPlayWhenAlreadyExistsAWinner() {
        Board board = boardService.init();

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());

        final var exception = Assertions.assertThrows(
                AlreadyWinnerException.class, () -> boardService
                        .play(board.getId(), board.getRows().get(0).getColumns().get(2).getId()));

        Assertions.assertEquals(
                String.format(AlreadyWinnerException.MESSAGE, 'X'), exception.getMessage());

    }

    private void mockColumnRepositorySave(final Column columnReturned) {
        when(columnRepository.save(Mockito.any(Column.class)))
                .thenReturn(Column.builder()
                        .id(columnId.getAndIncrement())
                        .code(UUID.randomUUID())
                        .createdAt(LocalDateTime.now()).build());
    }

    private Column.ColumnBuilder getBuildColumn(final LocalDateTime createdAt) {
        return Column.builder()
                .id(columnId.getAndIncrement())
                .code(UUID.randomUUID())
                .createdAt(createdAt);
    }

    private void mockRepositoryFindById(final Column columnReturned) {

        when(columnRepository.findById(columnReturned.getId())).thenReturn(Optional.of(columnReturned));
    }

}
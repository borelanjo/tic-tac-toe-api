package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.impl.BoardServiceImpl;
import com.borelanjo.tictoctoe.application.service.impl.ColumnServiceImpl;
import com.borelanjo.tictoctoe.application.service.impl.RowServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.repository.impl.BoardMemoryRepository;
import com.borelanjo.tictoctoe.repository.impl.ColumnMemoryRepository;
import com.borelanjo.tictoctoe.repository.impl.RowMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

    private BoardService boardService;

    @BeforeEach
    private void setup() {
        boardService = new BoardServiceImpl(
                new BoardMemoryRepository(),
                new RowServiceImpl(new RowMemoryRepository()),
                new ColumnServiceImpl(new ColumnMemoryRepository())
                );
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

        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(0).getColumns().get(2).getId());

        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(0).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(1).getId());
        boardService.play(board.getId(), board.getRows().get(1).getColumns().get(2).getId());

        boardService.play(board.getId(), board.getRows().get(2).getColumns().get(0).getId());

        Board result = boardService.find(board.getId());
        Assertions.assertEquals('X', result.getRows().get(0).getColumns().get(0).getSquare());
        Assertions.assertEquals('O', result.getRows().get(0).getColumns().get(1).getSquare());
        Assertions.assertEquals('X', result.getRows().get(0).getColumns().get(2).getSquare());

    }

}
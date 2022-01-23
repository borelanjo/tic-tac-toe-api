package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyPlayedThisColumnException;
import com.borelanjo.tictoctoe.application.service.exception.InvalidInputSquareException;
import com.borelanjo.tictoctoe.application.service.impl.ColumnServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.repository.ColumnRepository;
import com.borelanjo.tictoctoe.repository.impl.ColumnMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ColumnServiceTest {

    private ColumnService columnService;

    @BeforeEach
    void setUp() {
        ColumnRepository columnRepository = new ColumnMemoryRepository();
        columnService = new ColumnServiceImpl(columnRepository);
    }

    @Test
    void shouldInitANewColumnWhenRequest() {
        final var columnA = columnService.init();

        Assertions.assertNotNull(columnA);
        Assertions.assertEquals(1L, columnA.getId());
        Assertions.assertNull(columnA.getSquare());
    }

    @Test
    void shouldPlayWhenHaveInputX() {
        columnService.init();
        final var columnA = columnService.play(1L, 'X');

        Assertions.assertNotNull(columnA);
        Assertions.assertEquals(1L, columnA.getId());
        Assertions.assertEquals('X', columnA.getSquare());
    }

    @Test
    void shouldPlayWhenHaveInputO() {
        columnService.init();
        final var columnA = columnService.play(1L, 'O');

        Assertions.assertNotNull(columnA);
        Assertions.assertEquals(1L, columnA.getId());
        Assertions.assertEquals('O', columnA.getSquare());
    }

    @Test
    void shouldNotPlayWhenHaveInvalidInput() {
        columnService.init();
        final var invalidInput = '0';
        final var exception = Assertions.assertThrows(
                InvalidInputSquareException.class, () -> columnService.play(1L, invalidInput));

        Assertions.assertEquals(
                String.format(InvalidInputSquareException.MESSAGE, invalidInput), exception.getMessage());
    }

    @Test
    void shouldNotPlayTwiceInSameColumn() {
        columnService.init();
        columnService.play(1L, 'X');
        final var exception = Assertions.assertThrows(
                AlreadyPlayedThisColumnException.class, () -> columnService.play(1L, 'X'));

        Assertions.assertEquals(
                AlreadyPlayedThisColumnException.MESSAGE, exception.getMessage());
    }

    @Test
    void shouldNotPlayWhenColumnNotExists() {
        final var exception = Assertions.assertThrows(
                NoSuchElementException.class, () -> columnService.play(1L, 'X'));

        Assertions.assertEquals(
                "No value present", exception.getMessage());
    }
}
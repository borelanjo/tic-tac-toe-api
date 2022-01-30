package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.exception.AlreadyPlayedThisColumnException;
import com.borelanjo.tictoctoe.application.service.exception.InvalidInputSquareException;
import com.borelanjo.tictoctoe.application.service.impl.ColumnServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.ColumnRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class ColumnServiceTest {

    private ColumnService columnService;

    private ColumnRepository columnRepository;

    @BeforeEach
    void setUp() {
        columnRepository = Mockito.mock(ColumnRepository.class);
        columnService = new ColumnServiceImpl(columnRepository);
    }

    @Test
    void shouldInitANewColumnWhenRequest() {
        mockRepositorySave(getBuild(LocalDateTime.now()).build());
        final var columnA = columnService.init(null);

        Assertions.assertNotNull(columnA);
        Assertions.assertEquals(1L, columnA.getId());
        Assertions.assertNull(columnA.getSquare());
    }

    @ParameterizedTest(name = "shouldPlayWhenHaveInput{0}")
    @ValueSource(strings = {"O", "X"})
    void shouldPlayWhenHaveInput(final char square) {
        final LocalDateTime createdAt = LocalDateTime.of(2022, 6, 18, 18, 0, 0);
        final LocalDateTime expectedUpdateAt = LocalDateTime.of(2022, 6, 18, 20, 0, 0);
        final long expectedId = 1L;

        final var columnFind = getBuild(createdAt).build();
        mockRepositoryFindById(columnFind);

        mockRepositorySave(getBuild(createdAt)
                .square(square)
                .updatedAt(expectedUpdateAt)
                .build());

        final var columnA = columnService.play(expectedId, square);

        Assertions.assertNotNull(columnA);
        Assertions.assertEquals(expectedId, columnA.getId());
        Assertions.assertEquals(square, columnA.getSquare());
        Assertions.assertEquals(createdAt, columnA.getCreatedAt());
        Assertions.assertEquals(expectedUpdateAt, columnA.getUpdatedAt());
    }

    @Test
    void shouldNotPlayWhenHaveInvalidInput() {
        columnService.init(null);
        final var invalidInput = '0';
        final var exception = Assertions.assertThrows(
                InvalidInputSquareException.class, () -> columnService.play(1L, invalidInput));

        Assertions.assertEquals(
                String.format(InvalidInputSquareException.MESSAGE, invalidInput), exception.getMessage());
    }

    @Test
    void shouldNotPlayTwiceInSameColumn() {
        final LocalDateTime createdAt = LocalDateTime.of(2022, 6, 18, 18, 0, 0);
        final LocalDateTime expectedUpdateAt = LocalDateTime.of(2022, 6, 18, 20, 0, 0);
        final long expectedId = 1L;

        final var columnFind = getBuild(createdAt)
                .updatedAt(expectedUpdateAt).build();
        mockRepositoryFindById(columnFind);

        columnService.play(expectedId, 'X');
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

    private void mockRepositorySave(final Column columnReturned) {
        when(columnRepository.save(Mockito.any(Column.class)))
                .thenReturn(columnReturned);
    }

    private Column.ColumnBuilder getBuild(final LocalDateTime createdAt) {
        return Column.builder()
                .id(1L)
                .code(UUID.randomUUID())
                .createdAt(createdAt);
    }

    private void mockRepositoryFindById(final Column columnReturned) {

        when(columnRepository.findById(columnReturned.getId())).thenReturn(Optional.of(columnReturned));
    }
}
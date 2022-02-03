package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.impl.RowServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.infrastructure.persistence.repository.RowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class RowServiceTest {

    private RowService rowService;
    private RowRepository rowRepository;

    @BeforeEach
    void setUp() {
        rowRepository = Mockito.mock(RowRepository.class);
        rowService = new RowServiceImpl(rowRepository);
    }

    @Test
    void shouldInitANewRowWhenRequest() {

        final Board board = Board.builder()
                .id(1L)
                .build();

        final Row expectedRow = Row.builder()
                .board(board)
                .id(1L)
                .code(UUID.randomUUID())
                .position(RowPosition.TOP)
                .createdAt(LocalDateTime.now())
                .build();

        when(rowRepository.save(any())).thenReturn(expectedRow);

        final var rowOne = rowService.init(board, RowPosition.TOP);

        Assertions.assertNotNull(rowOne);
        Assertions.assertEquals(expectedRow.getId(), rowOne.getId());
        Assertions.assertEquals(expectedRow.getCode(), rowOne.getCode());
        Assertions.assertEquals(expectedRow.getPosition(), rowOne.getPosition());

    }

    @Test
    void shouldFindWhenExistRow() {
        final Board board = Board.builder()
                .id(1L)
                .build();

        final Row expectedRow = Row.builder()
                .board(board)
                .id(1L)
                .code(UUID.randomUUID())
                .position(RowPosition.TOP)
                .createdAt(LocalDateTime.now())
                .build();

        when(rowRepository.findById(1L)).thenReturn(Optional.of(expectedRow));

        Row row = rowService.find(1L);

        Assertions.assertNotNull(row);
        Assertions.assertEquals(expectedRow.getId(), row.getId());
        Assertions.assertEquals(expectedRow.getCode(), row.getCode());
        Assertions.assertEquals(expectedRow.getPosition(), row.getPosition());

    }

    @Test
    void shouldNotFindWhenNotExistRow() {
        final var exception = Assertions.assertThrows(
                NoSuchElementException.class, () -> rowService.find(1L));

        Assertions.assertEquals(
                "No value present", exception.getMessage());
    }

}
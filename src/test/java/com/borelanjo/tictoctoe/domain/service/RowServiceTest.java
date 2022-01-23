package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.service.impl.RowServiceImpl;
import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.repository.RowRepository;
import com.borelanjo.tictoctoe.repository.impl.RowMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class RowServiceTest {

    private RowService rowService;

    @BeforeEach
    void setUp() {
        RowRepository rowRepository = new RowMemoryRepository();
        rowService = new RowServiceImpl(rowRepository);
    }

    @Test
    void shouldInitANewColumnWhenRequest() {
        final var rowOne = rowService.init(null);

        Assertions.assertNotNull(rowOne);
        Assertions.assertEquals(1L, rowOne.getId());

    }

    @Test
    void shouldFindWhenExistRow() {
        rowService.init(null);
        Row row = rowService.find(1L);
        Assertions.assertNotNull(row);
        Assertions.assertEquals(1L, row.getId());

    }

    @Test
    void shouldNotFindWhenNotExistRow() {
        final var exception = Assertions.assertThrows(
                NoSuchElementException.class, () -> rowService.find(1L));

        Assertions.assertEquals(
                "No value present", exception.getMessage());
    }

}
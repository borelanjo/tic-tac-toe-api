package com.borelanjo.tictoctoe.domain.service;

import com.borelanjo.tictoctoe.application.consumer.BoardConsumer;
import com.borelanjo.tictoctoe.application.consumer.ColumnConsumer;
import com.borelanjo.tictoctoe.application.consumer.RowConsumer;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles("test")
class InitGameServiceIntegrationTest {

    @Autowired
    private InitGameService initGameService;

    @SpyBean
    private BoardConsumer boardConsumer;

    @SpyBean
    private RowConsumer rowConsumer;

    @SpyBean
    private ColumnConsumer columnConsumer;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Captor
    ArgumentCaptor<UUID> boardCodeCaptor;

    @Captor
    ArgumentCaptor<RowRequestTo> rowRequestCaptor;

    @Captor
    ArgumentCaptor<ColumnRequestTo> columnRequestCaptor;

    @Test
    public void shouldInitANewGameWhenRequest() {
        final UUID boardCode = UUID.randomUUID();
        initGameService.process(boardCode);

        //consumer
        verify(boardConsumer, timeout(100000).times(1)).receiveInit(boardCodeCaptor.capture());

        verify(rowConsumer, timeout(100000).times(3)).receiveInit(rowRequestCaptor.capture());

        verify(columnConsumer, timeout(100000).times(10)).receiveInit(columnRequestCaptor.capture());

        final Board board = boardService.find(boardCode);

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

    private Row getRow(final Board board, final RowPosition position) {
        return board.getRows().stream()
                .filter(r -> r.getPosition().equals(position)).findFirst().orElseThrow();
    }


    private Column getColumn(final Row row, final ColumnPosition position) {
        return row.getColumns().stream()
                .filter(c -> c.getPosition().equals(position)).findFirst().orElseThrow();
    }

}
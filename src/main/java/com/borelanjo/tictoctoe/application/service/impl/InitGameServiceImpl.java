package com.borelanjo.tictoctoe.application.service.impl;

import com.borelanjo.tictoctoe.application.producer.BoardProducer;
import com.borelanjo.tictoctoe.application.producer.ColumnProducer;
import com.borelanjo.tictoctoe.application.producer.RowProducer;
import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.domain.service.InitGameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class InitGameServiceImpl implements InitGameService {

    private final BoardProducer boardProducer;

    private final RowProducer rowProducer;

    private final ColumnProducer columnProducer;

    @Override
    public void process(final UUID boardCode) {
        boardProducer.sendToInit(boardCode);

        initRow(boardCode, RowPosition.TOP);
        initRow(boardCode, RowPosition.MIDDLE);
        initRow(boardCode, RowPosition.BOTTOM);
    }

    private void initRow(final UUID boardCode, final RowPosition rowPosition) {
        rowProducer.sendToInit(boardCode, rowPosition);

        columnProducer.sendToInit(boardCode, rowPosition, ColumnPosition.LEFT);
        columnProducer.sendToInit(boardCode, rowPosition, ColumnPosition.MIDDLE);
        columnProducer.sendToInit(boardCode, rowPosition, ColumnPosition.RIGHT);
    }
}

package com.borelanjo.tictoctoe.application.producer;

import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.presentation.dto.column.ColumnRequestTo;
import com.borelanjo.tictoctoe.presentation.dto.row.RowRequestTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ColumnProducer {

    private final KafkaTemplate kafkaTemplate;

    public void sendToInit(final UUID boardCode, final RowPosition rowPosition, final ColumnPosition position) {
        ColumnRequestTo requestTo = ColumnRequestTo
                .builder()
                .boardCode(boardCode)
                .rowPosition(rowPosition)
                .position(position)
                .build();
        kafkaTemplate.send("column-init", boardCode.toString(), requestTo);
        log.info("msg=Mensagem produzida, requestTo={}", requestTo);
    }

}

package com.borelanjo.tictoctoe.application.consumer;

import com.borelanjo.tictoctoe.domain.service.ColumnService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.presentation.dto.column.ColumnRequestTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ColumnConsumer {

    public static final String COLUMN_INIT_TOPIC = "column-init";

    private final RowService rowService;

    private final ColumnService columnService;

    @KafkaListener(topics = COLUMN_INIT_TOPIC)
    @Retryable(value = RuntimeException.class)
    public void receiveInit(@Payload ColumnRequestTo columnRequestTo) {

        try {
            var column = columnService.init(
                    rowService.find(columnRequestTo.getBoardCode(),
                            columnRequestTo.getRowPosition()), columnRequestTo.getPosition());

            log.info("msg=inicializando coluna, column={}", column);
        } catch (Exception e) {
            log.error("msg=Não foi possível produzir coluna, columnRequestTo={}, e={}", columnRequestTo, e.getMessage(), e);
            throw e;
        }


    }
}

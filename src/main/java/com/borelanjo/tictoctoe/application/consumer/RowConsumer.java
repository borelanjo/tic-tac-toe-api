package com.borelanjo.tictoctoe.application.consumer;

import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.domain.service.RowService;
import com.borelanjo.tictoctoe.presentation.dto.row.RowRequestTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RowConsumer {

    public static final String ROW_INIT_TOPIC = "row-init";

    private final RowService rowService;

    private final BoardService boardService;

    @KafkaListener(topics = ROW_INIT_TOPIC)
    @Retryable(value = RuntimeException.class)
    public void receiveInit(@Payload RowRequestTo rowRequestTo) {

        try {
            final var row = rowService.init(boardService.find(rowRequestTo.getBoardCode()), rowRequestTo.getPosition());
            log.info("msg=inicializando rows, row={}", row);
        } catch (Exception e) {
            log.error("msg=Não foi possível produzir linha, rowRequestTo={}, e={}", rowRequestTo, e.getMessage(), e);
            throw e;
        }

    }
}

package com.borelanjo.tictoctoe.application.consumer;

import com.borelanjo.tictoctoe.domain.model.Board;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BoardConsumer {

    public static final String BOARD_INIT_TOPIC = "board-init";
    public static final String TIC_TAC_TOE_GROUP_ID = "tic-tac-toe";

    private final BoardService boardService;

    @KafkaListener(topics = BOARD_INIT_TOPIC, groupId = TIC_TAC_TOE_GROUP_ID)
    @Retryable(value = RuntimeException.class)
    public void receiveInit(@Payload UUID boardCode) {
        try {
            final Board board = boardService.init(boardCode);
            log.info("msg=inicializando board, board={}", board);
        } catch (Exception e) {
            log.error("msg=Não foi possível produzir tabuleiro, boardCode={}, e={}", boardCode, e.getMessage(), e);
            throw e;
        }

    }
}

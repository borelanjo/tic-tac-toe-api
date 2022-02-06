package com.borelanjo.tictoctoe.application.producer;

import com.borelanjo.tictoctoe.domain.model.Board;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BoardProducer {

    private final KafkaTemplate kafkaTemplate;

    public void sendToInit(final UUID boardCode) {
        kafkaTemplate.send("board-init", boardCode.toString(), boardCode);
    }

}

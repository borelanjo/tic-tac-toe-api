package com.borelanjo.tictoctoe.application.producer;

import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.presentation.dto.row.RowRequestTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RowProducer {

    private final KafkaTemplate kafkaTemplate;

    public void sendToInit(final UUID boardCode, final RowPosition rowPosition) {
        RowRequestTo requestTo = RowRequestTo
                .builder()
                .boardCode(boardCode)
                .position(rowPosition)
                .build();
        kafkaTemplate.send("row-init", boardCode.toString(), requestTo);
        log.info("msg=Mensagem produzida, requestTo={}", requestTo);
    }

}

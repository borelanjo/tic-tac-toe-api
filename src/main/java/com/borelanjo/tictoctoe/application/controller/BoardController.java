package com.borelanjo.tictoctoe.application.controller;

import com.borelanjo.tictoctoe.application.producer.BoardProducer;
import com.borelanjo.tictoctoe.application.service.ResponseService;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.domain.service.InitGameService;
import com.borelanjo.tictoctoe.presentation.dto.ResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.board.BoardInitResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.board.BoardResponseTo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.borelanjo.tictoctoe.presentation.mapper.BoardMapper.from;

@RestController
@RequestMapping("/api/boards")
@AllArgsConstructor
public class BoardController {

    private final ResponseService responseService;

    private final BoardService boardService;

    private final InitGameService initGameService;

    @PostMapping
    public ResponseEntity<ResponseTo<BoardInitResponseTo>> init() {
        final UUID boardCode = UUID.randomUUID();
        initGameService.process(boardCode);

        return responseService.accepted(BoardInitResponseTo
                .builder()
                .code(boardCode)
                .build());
    }

    @PatchMapping("/{boardCode}/rows/{rowPosition}/columns/{columnPosition}")
    public ResponseEntity<ResponseTo<BoardResponseTo>> play(
            @PathVariable final UUID boardCode,
            @PathVariable final RowPosition rowPosition,
            @PathVariable final ColumnPosition columnPosition) {
        return responseService.ok(from(boardService.play(boardCode, rowPosition, columnPosition)));
    }

    @GetMapping("/{boardCode}")
    public ResponseEntity<ResponseTo<BoardResponseTo>> find(
            @PathVariable final UUID boardCode) {
        return responseService.ok(from(boardService.find(boardCode)));
    }
}

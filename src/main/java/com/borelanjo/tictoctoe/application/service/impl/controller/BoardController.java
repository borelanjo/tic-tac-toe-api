package com.borelanjo.tictoctoe.application.service.impl.controller;

import com.borelanjo.tictoctoe.application.service.ResponseService;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.presentation.dto.ResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.board.BoardResponseTo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.borelanjo.tictoctoe.presentation.mapper.BoardMapper.from;

@RestController
@RequestMapping("/api/boards")
@AllArgsConstructor
public class BoardController {

    private final ResponseService responseService;

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ResponseTo<BoardResponseTo>> init() {
        return responseService.ok(from(boardService.init()));
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

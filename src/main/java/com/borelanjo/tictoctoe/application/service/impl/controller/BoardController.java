package com.borelanjo.tictoctoe.application.service.impl.controller;

import com.borelanjo.tictoctoe.application.service.ResponseService;
import com.borelanjo.tictoctoe.domain.service.BoardService;
import com.borelanjo.tictoctoe.presentation.dto.ResponseTo;
import com.borelanjo.tictoctoe.presentation.dto.board.BoardResponseTo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{boardId}/columns/{columnId}")
    public ResponseEntity<ResponseTo<BoardResponseTo>> play(
            @PathVariable final Long boardId, @PathVariable final Long columnId) {
        return responseService.ok(from(boardService.play(boardId, columnId)));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseTo<BoardResponseTo>> find(
            @PathVariable final Long boardId) {
        return responseService.ok(from(boardService.find(boardId)));
    }
}

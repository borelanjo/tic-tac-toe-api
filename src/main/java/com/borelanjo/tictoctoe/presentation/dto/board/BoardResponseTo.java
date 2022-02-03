package com.borelanjo.tictoctoe.presentation.dto.board;

import com.borelanjo.tictoctoe.presentation.dto.row.RowResponseTo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BoardResponseTo {

    private UUID code;
    private Character input;
    private Character winner;
    private List<RowResponseTo> rows;
}

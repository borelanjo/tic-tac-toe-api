package com.borelanjo.tictoctoe.presentation.dto.board;

import com.borelanjo.tictoctoe.presentation.dto.row.RowResponseTo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoardResponseTo {

    private Long id;
    private Character input;
    private Character winner;
    private List<RowResponseTo> rows;
}

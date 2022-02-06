package com.borelanjo.tictoctoe.presentation.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class BoardInitResponseTo {

    private UUID code;

    @Builder.Default()
    private Character input = 'X';
}

package com.borelanjo.tictoctoe.presentation.dto.column;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColumnResponseTo {

    private Long id;
    private Character square;
}

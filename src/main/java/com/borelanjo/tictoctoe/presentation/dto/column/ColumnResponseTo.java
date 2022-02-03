package com.borelanjo.tictoctoe.presentation.dto.column;

import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColumnResponseTo {

    private ColumnPosition position;
    private Character square;
}

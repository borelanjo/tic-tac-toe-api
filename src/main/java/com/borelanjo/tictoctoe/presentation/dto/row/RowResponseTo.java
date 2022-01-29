package com.borelanjo.tictoctoe.presentation.dto.row;

import com.borelanjo.tictoctoe.presentation.dto.column.ColumnResponseTo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RowResponseTo {

    private List<ColumnResponseTo> columns;
}

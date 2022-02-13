package com.borelanjo.tictoctoe.presentation.dto.row;

import com.borelanjo.tictoctoe.domain.model.RowPosition;
import com.borelanjo.tictoctoe.presentation.dto.column.ColumnResponseTo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RowRequestTo {

    private UUID boardCode;

    private RowPosition position;

    private List<ColumnResponseTo> columns;
}

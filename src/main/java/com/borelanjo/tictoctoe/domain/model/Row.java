package com.borelanjo.tictoctoe.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Row {
    private Long id;
    private List<Column> columns;

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", columns=" + columns +
                '}';
    }
}

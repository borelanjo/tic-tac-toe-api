package com.borelanjo.tictoctoe.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Board {

    private Long id;
    private Character input;
    private List<Row> rows;
    private Character winner;

    @Override
    public String toString() {
        return String.format("Board{id=%s, input=%s, winner=%s, \nrows=[\n%s,\n%s\n%s]\n}",
                getId(),
                getInput(),
                getWinner(),
                getRows().get(0),
                getRows().get(1),
                getRows().get(2));
    }
}

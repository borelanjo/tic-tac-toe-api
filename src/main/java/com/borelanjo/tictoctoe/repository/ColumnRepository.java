package com.borelanjo.tictoctoe.repository;

import com.borelanjo.tictoctoe.domain.model.Column;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository {

    Column init();

    Column play(Column column, Character square);

    Column find(Long id);
}

package com.borelanjo.tictoctoe.infrastructure.persistence.repository;

import com.borelanjo.tictoctoe.domain.model.Row;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {

    Row findByBoardCodeAndPosition(final UUID boardCode, final RowPosition position);


}

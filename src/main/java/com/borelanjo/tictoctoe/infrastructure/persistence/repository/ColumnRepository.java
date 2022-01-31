package com.borelanjo.tictoctoe.infrastructure.persistence.repository;

import com.borelanjo.tictoctoe.domain.model.Column;
import com.borelanjo.tictoctoe.domain.model.ColumnPosition;
import com.borelanjo.tictoctoe.domain.model.RowPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {

    Optional<Column> findByPositionAndRowPositionAndRowBoardCode(
            ColumnPosition position, RowPosition rowPosition, UUID rowBoardCode);

}

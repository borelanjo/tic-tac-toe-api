package com.borelanjo.tictoctoe.infrastructure.persistence.repository;

import com.borelanjo.tictoctoe.domain.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowRepository extends JpaRepository<Row, Long> {


}

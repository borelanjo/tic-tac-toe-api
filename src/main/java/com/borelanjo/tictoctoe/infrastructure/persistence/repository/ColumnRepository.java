package com.borelanjo.tictoctoe.infrastructure.persistence.repository;

import com.borelanjo.tictoctoe.domain.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {

}

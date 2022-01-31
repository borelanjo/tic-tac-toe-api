package com.borelanjo.tictoctoe.infrastructure.persistence.repository;

import com.borelanjo.tictoctoe.domain.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByCode(UUID code);

}
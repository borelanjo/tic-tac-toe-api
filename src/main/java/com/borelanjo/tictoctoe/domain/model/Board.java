package com.borelanjo.tictoctoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_board", schema = "game")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_board")
    private Long id;

    @Column(name = "code_board")
    private UUID code;

    @Column(name="input_board")
    private Character input;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @OrderBy(value = "id")
    private Set<Row> rows;

    @Column(name="winner_board")
    private Character winner;

    @javax.persistence.Column(name = "created_at")
    private LocalDateTime createdAt;

    @javax.persistence.Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return id.equals(board.id) && code.equals(board.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Board.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code=" + code)
                .add("input=" + input)
                .add("rows=" + rows)
                .add("winner=" + winner)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }
}

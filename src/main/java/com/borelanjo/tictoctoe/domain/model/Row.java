package com.borelanjo.tictoctoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_row", schema = "game")
public class Row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "id_row")
    private Long id;

    @javax.persistence.Column(name = "code_row")
    private UUID code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_board")
    private Board board;

    @Enumerated(EnumType.STRING)
    @javax.persistence.Column(name = "position_row")
    private RowPosition position;

    @OneToMany(mappedBy = "row", fetch = FetchType.EAGER)
    @OrderBy(value = "id")
    private Set<Column> columns;

    @javax.persistence.Column(name = "created_at")
    private LocalDateTime createdAt;

    @javax.persistence.Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return id.equals(row.id) && code.equals(row.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return "Row{" +
                "position=" + position +
                ", columns=" + columns +
                '}';
    }
}

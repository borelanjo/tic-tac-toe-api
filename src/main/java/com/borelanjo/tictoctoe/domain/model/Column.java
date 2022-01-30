package com.borelanjo.tictoctoe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_column", schema = "game")
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Column(name = "id_column")
    private Long id;

    @javax.persistence.Column(name = "code_column")
    private UUID code;

    @javax.persistence.Column
    private Character square;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_row")
    private Row row;

    @javax.persistence.Column(name = "created_at")
    private LocalDateTime createdAt;

    @javax.persistence.Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return id.equals(column.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return square != null ? square.toString() : " ";
    }
}

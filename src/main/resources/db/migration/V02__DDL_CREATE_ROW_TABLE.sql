CREATE SCHEMA IF NOT EXISTS game;
CREATE table game.t_row (
    id_row BIGSERIAL PRIMARY KEY,
    code_row UUID NOT NULL,
    id_board BIGINT NOT NULL,
    position_row VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_row_code_row UNIQUE (code_row),
    CONSTRAINT uk_id_board_position_row UNIQUE (id_board,position_row)
);

ALTER TABLE game.t_column
   ADD CONSTRAINT fk_column_row_id_row
   FOREIGN KEY (id_row)
   REFERENCES t_row(id_row);
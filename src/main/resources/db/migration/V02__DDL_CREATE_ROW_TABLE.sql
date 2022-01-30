CREATE SCHEMA IF NOT EXISTS game;
CREATE table game.t_row (
    id_row BIGSERIAL PRIMARY KEY,
    code_row UUID NOT NULL,
    id_board BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_row_code_row UNIQUE (code_row)
);

ALTER TABLE game.t_column
   ADD CONSTRAINT fk_column_row_id_row
   FOREIGN KEY (id_row)
   REFERENCES t_row(id_row);
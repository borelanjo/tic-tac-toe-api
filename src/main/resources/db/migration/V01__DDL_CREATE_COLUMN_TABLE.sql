CREATE SCHEMA IF NOT EXISTS game;
create table game.t_column (
    id_column BIGSERIAL PRIMARY KEY,
    code_column UUID NOT NULL,
    square CHAR,
    id_row BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_column_code_column UNIQUE (code_column)
);
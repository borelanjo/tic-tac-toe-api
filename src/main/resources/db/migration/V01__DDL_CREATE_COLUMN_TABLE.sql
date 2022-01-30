CREATE SCHEMA IF NOT EXISTS game;
create table game.t_column (
    id BIGSERIAL PRIMARY KEY,
    code UUID NOT NULL,
    square CHAR,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
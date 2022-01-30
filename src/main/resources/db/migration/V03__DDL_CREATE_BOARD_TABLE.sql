CREATE SCHEMA IF NOT EXISTS game;
CREATE table game.t_board (
    id_board BIGSERIAL PRIMARY KEY,
    code_board UUID NOT NULL,
    input_board CHAR NOT NULL,
    winner_board CHAR,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT uk_board_code_board UNIQUE (code_board)
);

ALTER TABLE game.t_row
   ADD CONSTRAINT fk_row_board_id_board
   FOREIGN KEY (id_board)
   REFERENCES t_board(id_board);
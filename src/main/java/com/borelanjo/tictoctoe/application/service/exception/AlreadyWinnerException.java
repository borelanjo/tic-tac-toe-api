package com.borelanjo.tictoctoe.application.service.exception;

public class AlreadyWinnerException extends RuntimeException{

    public static final String MESSAGE = "O %s já venceu esse tabuleiro";

    public AlreadyWinnerException(final Character winner) {
        super(String.format(MESSAGE, winner));
    }
}

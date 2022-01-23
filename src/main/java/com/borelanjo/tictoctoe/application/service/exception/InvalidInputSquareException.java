package com.borelanjo.tictoctoe.application.service.exception;

public class InvalidInputSquareException extends RuntimeException{

    public static final String MESSAGE = "%s não é uma opção válida. Escolha entre 'X' ou 'O'";

    public InvalidInputSquareException(final Character value) {
        super(String.format(MESSAGE, value));
    }
}

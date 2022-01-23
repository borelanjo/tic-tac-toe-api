package com.borelanjo.tictoctoe.application.service.exception;

public class AlreadyPlayedThisColumnException extends RuntimeException{

    public static final String MESSAGE = "Já existe uma jogada nesse quadrado";

    public AlreadyPlayedThisColumnException() {
        super(MESSAGE);
    }
}

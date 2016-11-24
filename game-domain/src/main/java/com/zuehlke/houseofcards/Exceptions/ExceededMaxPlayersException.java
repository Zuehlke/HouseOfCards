package com.zuehlke.houseofcards.Exceptions;


public class ExceededMaxPlayersException extends RuntimeException {
    public ExceededMaxPlayersException(String message) {
        super(message);
    }
}

package com.zuehlke.hoc.Exceptions;


public class ExceededMaxPlayersException extends RuntimeException {
    public ExceededMaxPlayersException(String message) {
        super(message);
    }
}
